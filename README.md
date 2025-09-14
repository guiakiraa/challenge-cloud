# TriaTag

## Integrantes do Projeto

| Nome             | RM       |
|------------------|----------|
| Alice Nunes      | 559052   |
| Anne Rezendes    | 556779   |
| Guilherme Akira  | 556128   |

---

## Descrição do Projeto

A **TriaTag** foi desenvolvida para resolver o problema de desorganização na movimentação de motos dentro do pátio da Mottu. Com o uso de câmeras e um sistema inteligente, a TriaTag identifica e organiza automaticamente as motos por setor.

### 1. Iniciar Triagem

Após a moto ser analisada e diagnosticada na triagem, o funcionário seleciona na aplicação para qual setor a moto deve ser colocada. Se por um acaso a mesma for posta no setor errado, o sistema impedirá o cadastro dessa moto em tal setor.

### 2. Localizar Moto

Na aplicação, ao inserir a placa da moto:
- O sistema informa **em qual setor** a moto está localizada.
- É possível **acionar a buzina e o pisca-alerta** remotamente (via IoT), ajudando o funcionário a encontrar a moto rapidamente dentro do pátio.

### 3. Ver Motos no Pátio

O usuário:
- Informa o **código da filial**.
- Seleciona o **setor desejado**.
- O sistema lista **todas as motos presentes naquele setor**, em tempo real.

---

## 📘 Projeto Mottu - API com Azure SQL Server + Azure Container Instance  

Este projeto provisiona um ambiente completo no **Microsoft Azure** para rodar uma **API em Java** conectada a um **Azure SQL Database**, utilizando **Azure Container Registry (ACR)** e **Azure Container Instance (ACI)**.  

---  

### 🚀 Arquitetura  

1. **Azure Resource Group** – Agrupa todos os recursos.  
2. **Azure SQL Database** – Banco de dados com tabelas e dados iniciais.  
3. **Azure Container Registry (ACR)** – Repositório privado de imagens Docker.  
4. **Azure Container Instance (ACI)** – Container rodando a API em Java, exposto publicamente na porta **8080**.  

---

### 📦 Provisionamento dos Recursos  

#### 1️⃣ Criar Resource Group  
```bash
az group create --name rg-mottu --location eastus2
```

#### 2️⃣ Registrar provedor do SQL Server  
```bash
az provider register --namespace Microsoft.Sql
```

#### 3️⃣ Criar o SQL Server  
```bash
az sql server create   --name sql-server-mottu   --resource-group rg-mottu   --location eastus2   --admin-user user-mottu   --admin-password Fiap@2tdsvms   --enable-public-network true
```

#### 4️⃣ Criar banco de dados  
```bash
az sql db create   --resource-group rg-mottu   --server sql-server-mottu   --name db-mottu   --service-objective Basic   --backup-storage-redundancy Local   --zone-redundant false
```

#### 5️⃣ Liberar acesso externo ao SQL  
```bash
az sql server firewall-rule create   --resource-group rg-mottu   --server sql-server-mottu   --name liberaGeral   --start-ip-address 0.0.0.0   --end-ip-address 255.255.255.255
```

---

### 🗄️ Criação das Tabelas e Dados  

Conecte-se ao banco com **PowerShell** e rode o script:  

```powershell
Invoke-Sqlcmd -ServerInstance "sql-server-mottu.database.windows.net" `
              -Database "db-mottu" `
              -Username "user-mottu" `
              -Password "Fiap@2tdsvms" `
              -Query @"
-- Script para a criação das tabelas no SQL Server
-- Tabela de Endereços
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='endereco' AND xtype='U')
BEGIN
    CREATE TABLE endereco (
        id BIGINT IDENTITY(1,1) PRIMARY KEY,
        logradouro VARCHAR(255) NOT NULL,
        numero INT NOT NULL,
        bairro VARCHAR(100) NOT NULL,
        cidade VARCHAR(100) NOT NULL,
        estado CHAR(2) NOT NULL,
        cep CHAR(9) NOT NULL,
        complemento VARCHAR(255)
    );
END;

-- Tabela de Filiais
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='filial' AND xtype='U')
BEGIN
    CREATE TABLE filial (
        id BIGINT IDENTITY(1,1) PRIMARY KEY,
        nome VARCHAR(100) NOT NULL,
        fk_endereco BIGINT NOT NULL,
        CONSTRAINT FK_filial_endereco FOREIGN KEY (fk_endereco) REFERENCES endereco(id)
    );
END;

-- Tabela de Motos
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='moto' AND xtype='U')
BEGIN
    CREATE TABLE moto (
        id BIGINT IDENTITY(1,1) PRIMARY KEY,
        placa VARCHAR(7) NOT NULL,
        ano INT NOT NULL,
        modelo VARCHAR(50) NOT NULL,
        tipo_combustivel VARCHAR(50) NOT NULL,
        fk_filial BIGINT NOT NULL,
        CONSTRAINT FK_moto_filial FOREIGN KEY (fk_filial) REFERENCES filial(id)
    );
END;

-- Tabela de Funcionários
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='funcionario' AND xtype='U')
BEGIN
    CREATE TABLE funcionario (
        id BIGINT IDENTITY(1,1) PRIMARY KEY,
        nome VARCHAR(150) NOT NULL,
        fk_filial BIGINT NOT NULL,
        CONSTRAINT FK_funcionario_filial FOREIGN KEY (fk_filial) REFERENCES filial(id)
    );
END;

-- Tabela de Localizações
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='localizacao' AND xtype='U')
BEGIN
    CREATE TABLE localizacao (
        id BIGINT IDENTITY(1,1) PRIMARY KEY,
        pontox FLOAT NOT NULL,
        pontoy FLOAT NOT NULL,
        data_hora DATETIME NOT NULL,
        fonte VARCHAR(50) NOT NULL,
        fk_moto BIGINT NOT NULL,
        CONSTRAINT FK_localizacao_moto FOREIGN KEY (fk_moto) REFERENCES moto(id)
    );
END;


INSERT INTO ENDERECO (NUMERO, CEP, BAIRRO, CIDADE, ESTADO, COMPLEMENTO, LOGRADOURO) VALUES ('101', '01001-000', 'Sé', 'São Paulo', 'SP', 'Edifício Central', 'Praça da Sé');
INSERT INTO ENDERECO (NUMERO, CEP, BAIRRO, CIDADE, ESTADO, COMPLEMENTO, LOGRADOURO) VALUES ('789', '20040-030', 'Centro', 'Rio de Janeiro', 'RJ', NULL, 'Av. Rio Branco');
INSERT INTO ENDERECO (NUMERO, CEP, BAIRRO, CIDADE, ESTADO, COMPLEMENTO, LOGRADOURO) VALUES ('321', '30130-005', 'Savassi', 'Belo Horizonte', 'MG', 'Loja 5', 'Rua Antônio de Albuquerque');
INSERT INTO ENDERECO (NUMERO, CEP, BAIRRO, CIDADE, ESTADO, COMPLEMENTO, LOGRADOURO) VALUES ('654', '90010-150', 'Centro Histórico', 'Porto Alegre', 'RS', 'Conjunto 2B', 'Rua da Praia');
INSERT INTO ENDERECO (NUMERO, CEP, BAIRRO, CIDADE, ESTADO, COMPLEMENTO, LOGRADOURO) VALUES ('987', '71000-000', 'Asa Sul', 'Brasília', 'DF', 'Bloco D', 'SQS 105');
INSERT INTO ENDERECO (NUMERO, CEP, BAIRRO, CIDADE, ESTADO, COMPLEMENTO, LOGRADOURO) VALUES ('1234', '80020-300', 'Centro', 'Curitiba', 'PR', 'Sala 10', 'Rua XV de Novembro');
INSERT INTO ENDERECO (NUMERO, CEP, BAIRRO, CIDADE, ESTADO, COMPLEMENTO, LOGRADOURO) VALUES ('567', '40020-000', 'Comércio', 'Salvador', 'BA', NULL, 'Av. Contorno');
INSERT INTO ENDERECO (NUMERO, CEP, BAIRRO, CIDADE, ESTADO, COMPLEMENTO, LOGRADOURO) VALUES ('890', '60115-170', 'Meireles', 'Fortaleza', 'CE', 'Andar 3', 'Av. Beira Mar');
INSERT INTO ENDERECO (NUMERO, CEP, BAIRRO, CIDADE, ESTADO, COMPLEMENTO, LOGRADOURO) VALUES ('112', '50010-240', 'Santo Antônio', 'Recife', 'PE', 'Sala 401', 'Rua do Sol');
INSERT INTO ENDERECO (NUMERO, CEP, BAIRRO, CIDADE, ESTADO, COMPLEMENTO, LOGRADOURO) VALUES ('335', '13013-010', 'Centro', 'Campinas', 'SP', 'Fundos', 'Rua Treze de Maio');

INSERT INTO FILIAL (FK_ENDERECO, NOME) VALUES (1, 'Filial Sé');
INSERT INTO FILIAL (FK_ENDERECO, NOME) VALUES (2, 'Filial Centro RJ');
INSERT INTO FILIAL (FK_ENDERECO, NOME) VALUES (3, 'Filial Savassi');
INSERT INTO FILIAL (FK_ENDERECO, NOME) VALUES (4, 'Filial Porto Alegre');
INSERT INTO FILIAL (FK_ENDERECO, NOME) VALUES (5, 'Filial Brasília Sul');
INSERT INTO FILIAL (FK_ENDERECO, NOME) VALUES (6, 'Filial Curitiba Centro');
INSERT INTO FILIAL (FK_ENDERECO, NOME) VALUES (7, 'Filial Salvador Comércio');
INSERT INTO FILIAL (FK_ENDERECO, NOME) VALUES (8, 'Filial Fortaleza Beira Mar');
INSERT INTO FILIAL (FK_ENDERECO, NOME) VALUES (9, 'Filial Recife Antigo');
INSERT INTO FILIAL (FK_ENDERECO, NOME) VALUES (10, 'Filial Campinas Centro');

INSERT INTO FUNCIONARIO (FK_FILIAL, NOME) VALUES (1, 'Ana Souza');
INSERT INTO FUNCIONARIO (FK_FILIAL, NOME) VALUES (1, 'Bruno Costa');
INSERT INTO FUNCIONARIO (FK_FILIAL, NOME) VALUES (2, 'Carla Lima');
INSERT INTO FUNCIONARIO (FK_FILIAL, NOME) VALUES (3, 'Daniel Pereira');
INSERT INTO FUNCIONARIO (FK_FILIAL, NOME) VALUES (4, 'Eduarda Santos');
INSERT INTO FUNCIONARIO (FK_FILIAL, NOME) VALUES (5, 'Fernando Rodrigues');
INSERT INTO FUNCIONARIO (FK_FILIAL, NOME) VALUES (6, 'Gabriela Fernandes');
INSERT INTO FUNCIONARIO (FK_FILIAL, NOME) VALUES (7, 'Hugo Almeida');
INSERT INTO FUNCIONARIO (FK_FILIAL, NOME) VALUES (8, 'Isabela Gomes');
INSERT INTO FUNCIONARIO (FK_FILIAL, NOME) VALUES (9, 'Julio Martins');

INSERT INTO MOTO (ANO, FK_FILIAL, PLACA, MODELO, TIPO_COMBUSTIVEL) VALUES (2024, 1, 'GHI4J56', 'MOTTU_SPORT', 'Gasolina');
INSERT INTO MOTO (ANO, FK_FILIAL, PLACA, MODELO, TIPO_COMBUSTIVEL) VALUES (2023, 1, 'KLM7N89', 'MOTTU_POP', 'Eletrico');
INSERT INTO MOTO (ANO, FK_FILIAL, PLACA, MODELO, TIPO_COMBUSTIVEL) VALUES (2022, 2, 'OPQ1R23', 'MOTTU_E', 'Gasolina');
INSERT INTO MOTO (ANO, FK_FILIAL, PLACA, MODELO, TIPO_COMBUSTIVEL) VALUES (2024, 3, 'STU4V56', 'MOTTU_SPORT', 'Gasolina');
INSERT INTO MOTO (ANO, FK_FILIAL, PLACA, MODELO, TIPO_COMBUSTIVEL) VALUES (2023, 4, 'WXY7Z89', 'MOTTU_POP', 'Eletrico');
INSERT INTO MOTO (ANO, FK_FILIAL, PLACA, MODELO, TIPO_COMBUSTIVEL) VALUES (2021, 5, 'BCD1E23', 'MOTTU_E', 'Gasolina');
INSERT INTO MOTO (ANO, FK_FILIAL, PLACA, MODELO, TIPO_COMBUSTIVEL) VALUES (2024, 6, 'FGH4I56', 'MOTTU_SPORT', 'Gasolina');
INSERT INTO MOTO (ANO, FK_FILIAL, PLACA, MODELO, TIPO_COMBUSTIVEL) VALUES (2022, 7, 'JKL7M89', 'MOTTU_POP', 'Eletrico');
INSERT INTO MOTO (ANO, FK_FILIAL, PLACA, MODELO, TIPO_COMBUSTIVEL) VALUES (2023, 8, 'NÃO0P12', 'MOTTU_E', 'Gasolina');
INSERT INTO MOTO (ANO, FK_FILIAL, PLACA, MODELO, TIPO_COMBUSTIVEL) VALUES (2024, 9, 'QRS3T45', 'MOTTU_SPORT', 'Gasolina');

INSERT INTO LOCALIZACAO (PONTOX, PONTOY, DATA_HORA, FK_MOTO, FONTE) VALUES (-46.633309, -23.550520, '2025-05-19 09:00:00', 1, 'GPS');
INSERT INTO LOCALIZACAO (PONTOX, PONTOY, DATA_HORA, FK_MOTO, FONTE) VALUES (-46.640000, -23.560000, '2025-05-19 09:05:00', 1, 'GPS');
INSERT INTO LOCALIZACAO (PONTOX, PONTOY, DATA_HORA, FK_MOTO, FONTE) VALUES (-43.172897, -22.906847, '2025-05-19 09:10:00', 3, 'Visao_Computacional');
INSERT INTO LOCALIZACAO (PONTOX, PONTOY, DATA_HORA, FK_MOTO, FONTE) VALUES (-43.180000, -22.910000, '2025-05-19 09:15:00', 3, 'Sensor');
INSERT INTO LOCALIZACAO (PONTOX, PONTOY, DATA_HORA, FK_MOTO, FONTE) VALUES (-47.882432, -15.794229, '2025-05-19 09:20:00', 5, 'GPS');
INSERT INTO LOCALIZACAO (PONTOX, PONTOY, DATA_HORA, FK_MOTO, FONTE) VALUES (-47.890000, -15.800000, '2025-05-19 09:25:00', 5, 'Visao_Computacional');
INSERT INTO LOCALIZACAO (PONTOX, PONTOY, DATA_HORA, FK_MOTO, FONTE) VALUES (-51.220000, -30.030000, '2025-05-19 09:30:00', 4, 'Sensor');
INSERT INTO LOCALIZACAO (PONTOX, PONTOY, DATA_HORA, FK_MOTO, FONTE) VALUES (-51.230000, -30.040000, '2025-05-19 09:35:00', 4, 'GPS');
INSERT INTO LOCALIZACAO (PONTOX, PONTOY, DATA_HORA, FK_MOTO, FONTE) VALUES (-38.520000, -3.730000, '2025-05-19 09:40:00', 8, 'Visao_Computacional');
INSERT INTO LOCALIZACAO (PONTOX, PONTOY, DATA_HORA, FK_MOTO, FONTE) VALUES (-38.530000, -3.740000, '2025-05-19 09:45:00', 8, 'Sensor');	
"@
```

---

### 🐳 Construindo e Publicando a Imagem  

#### 1️⃣ Build da imagem  
```bash
docker build -f Dockerfile -t mottu-java .
```

#### 2️⃣ Criar o ACR  
```bash
az acr create --resource-group rg-mottu --name apitriajava   --sku Standard --location eastus   --public-network-enabled true --admin-enabled true
```

#### 3️⃣ Login no ACR  
```bash
az acr login --name apitriajava
```

#### 4️⃣ Tag e Push da Imagem  
```bash
docker tag mottu-java apitriajava.azurecr.io/mottu-java:v1
docker push apitriajava.azurecr.io/mottu-java:v1
```

---

### ☁️ Deploy no Azure Container Instance  

```bash
az container create   --resource-group rg-mottu   --name apicontainer   --image apitriajava.azurecr.io/mottu-java:v1   --cpu 1 --memory 1   --registry-login-server apitriajava.azurecr.io   --registry-username apitriajava   --registry-password <SENHA_DO_ACR>   --os-type Linux   --ip-address Public   --ports 8080   --environment-variables     DB_URL="jdbc:sqlserver://sql-server-mottu.database.windows.net:1433;database=db-mottu;user=user-mottu@sql-server-mottu;password=Fiap@2tdsvms;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;"     DB_USER="user-mottu"     DB_PASS="Fiap@2tdsvms"
```

---

### 🔗 Acesso à API  

Depois do deploy, copie o **IP público** do container e acesse:  

```
http://<IP_CONTAINER>:8080/swagger-ui/index.html
```

---

### 🛠️ Testando Conexão com Banco  

Conecte-se ao SQL Server:  

```
Server: sql-server-mottu.database.windows.net
User:   user-mottu
Pass:   Fiap@2tdsvms
DB:     db-mottu
```

Exemplo de consulta:  
```sql
SELECT * FROM [dbo].[endereco];
```

---

### 📑 Exemplos de Requisições  

#### POST – Criar endereço  
```json
{
  "logradouro": "Avenida Paulista",
  "numero": 1578,
  "bairro": "Bela Vista",
  "cidade": "São Paulo",
  "estado": "SP",
  "cep": "01310-200",
  "complemento": "Apartamento 72"
}
```

#### PUT – Atualizar endereço (id=11)  
```json
{
  "logradouro": "Avenida Paulista",
  "numero": 1000,
  "bairro": "Bela Vista",
  "cidade": "São Paulo",
  "estado": "SP",
  "cep": "01310-200",
  "complemento": "Sala 302"
}
```

---


