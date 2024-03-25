PT-BR

**** ATENÇÃO *****
Se NÃO deseja utilizar a importação, por favor, siga o passos abaixo.

- Comente o trecho de código abaixo na classe principal: EightbankConsoleApplication.java, antes de compilar.

clientService.importClientsFromFile("clients.csv");

###################################################################################################################

### Automação para importação de clientes ###

- Inserir arquivo exclusivamente no formato '.csv', no diretório raíz da aplicação

#### LAYOUT DO ARQUIVO #####

cpf,renda,email,nome,data de nascimento,rua,numero,bairro,cidade,estado,cep,telefone,senha


-> Regras

cpf: Deve conter 11 números, sem separação por pontos ou hífen
renda: Deve ser um numero positivo e valido
email: Deve ser um endereço de e-mail válido
data de nascimento: Deve ser no formato (dd/mm/aaaa)
cep: Deve ser alfanumérico, no formato: XXXXX-XXX
telefone: Deve ser alfanúmerico, no formato (XX) XXXX(X)-XXXX. Detalhe o 5º digito é opcional
senha: alfanumérica

Os demais capmpos devem ser de no mínimo 2 caracteres e no máximo 100 caracteres.

******************************************************************************************************************

EN-UK

**** WARNING *****
If you DO NOT want to use the client import feature, please follow the instructions bellow.

- Comment the code snippet below in the main class: EightbankConsoleApplication.java, before compiling.
-> clientService.importClientsFromFile("clients.csv");

#### Clients import automation ####

- Insert files exclusively in the '.csv' format, in the root directory

#### FILE LAYOUT #####

brazilianCPF,grossMonthlyIncome,email,name,dateOfBirth,streetName,number,district,city,state,brazilianZipCode,phoneNumber,password

*** There is either no HEADER or TRAILER ***

-> RULES:

cpf: It must be a number containing 11 positions, without any kind of either alphanumeric character or blank spaces
grossMonthlyIncome: It must be a valid and positive number
email: It must be a valida email address
data de nascimento: It must follow the Brazilian pattern (dd/mm/aaaa)
cep: It must be alphanumeric and containing hyphen, following the pattern: XXXXX-XXX
phoneNumber: It must be alphanumeric, following the pattern (XX) XXXX(X)-XXXX. The 5th digit, just before hyphen, is optional.
password: alphanumeric

The other fields must be alphanumeric with at least 2 characters and at maximum 100 characters.


