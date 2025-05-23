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

## Instruções para Executar

### Pré-requisitos

- Java JDK 17+ instalado
- Git instalado

### Clonando o repositório

```bash
git clone https://github.com/guiakiraa/java-mottu.git
cd java-mottu
```

### Executando o projeto

Com o Maven Wrapper:

```bash
./mvnw spring-boot:run
```

Agora acesse a aplicação em:

```
http://localhost:8080/swagger-ui/index.html#/
```
