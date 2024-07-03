## Passo a passo para ter acesso ao projeto
#### ● Abra o seu GitHub
#### ● Pesquise por Namibo06
#### ● Vá em "Repositories" e escolha o projeto "TrilhaBackEndJR-JUN15"
#### ● Clique no botão "<> Code",escolha entre HTTPS,SSH e GitHub CLI
#### ● Seguindo exemplo do HTTPS,execute o comando abaixo no seu terminal,na pasta em que deseja que o projeto esteja
#### ```git clone https://github.com/Namibo06/TrilhaBackEndJR-JUN15.git```



-----------------------------------------------------------------------------------------------
## Sobre o projeto
### É uma API RESTFull,ou seja,segue alguns padrões,um deles é ter os métodos HTTP bem definidos como (GET,POST,PUT,DELETE),e o que essa API faz?
#### ● Cria Tarefas
#### ● Lista Tarefas
#### ● Atualiza Tarefas
#### ● Deleta Tarefas

### Simplesmente um CRUD (CREATE-READ-UPDATE-DELETE),como é uma API,não tem sua interface,porém mesmo sem uma interface através de endpoint's pode-se ser testada,seja através de testes dentro do sistema (Exemplo: Testes Unitários) ou testes externos (Exemplo: Insomnia)
### Tecnologias utilizadas:
#### ● Back-end: SpringBoot 3.3.1
#### ● Database: SQLite
#### ● Host: Railway
#### ● Dependências: Lombok,Spring JPA,ModelMapper,Hibernate Dialects,SpringBoot DevTools,Spring Validation



-----------------------------------------------------------------------------
## Documentação
### Estrutura do projeto:
```
meu-projeto/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── codigoCerto/
│   │   │           └── tarefas/
│   │   │               ├── configs/
│   │   │               │   └──Configs
│   │   │               ├── controllers/
│   │   │               │   └── UserController
│   │   │               ├── dtos/
│   │   │               │   └── ResponseApiMessageStatus
│   │   │               │   └── ResponsePasswordDTO
│   │   │               │   └── ResponseUserDTO
│   │   │               │   └── UserDTO
│   │   │               ├── models/
│   │   │               │   └── User
│   │   │               ├── repositories/
│   │   │               │   └── UserRepository
│   │   │               ├── services/
│   │   │               │   └── UserService
│   │   │               └── TarefasApplication
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/
│               └── codigoCerto/
│                   └── tarefas/
│                       └── TarefasApplicationTest
│                       └── TestUser
├── database.db
├── pom.xml
└── README.md
```


-------------------------------------------------------------------
## Configs
### ■ Configs
#### Utilizei esta class para receber um "@Bean" da class ModelMapper, que serve para mapeamento de entidade para DTO ou vice versa,como Models são o que vão para o banco e o que retornam deles,o que vem por meio de requisição e o que é retornado é um DTO,e por isso deve-se ser convertidos.



-------------------------------------------------------------------
## Models
### ■ Tabela User - "tb_user"
### Atributos: 
#### ● id - Long - Chave Primária
#### ● username - String - Não Nulo | Tamanho variável até 20 caracteres 
#### ● email - String - Não Nulo | Único | Tamanho variável até 120 caracteres
#### ● password - String - Não Nulo | Único | Tamanho variável até 100 caracteres
#### ● token - String


### ■ Tabela Task - "tb_task"
### Atributos:
#### ●


---------------------------------------------------------------------
## DTO's
### ResponseApiMessageStatus
### Atributos
#### ● message - String
#### ● status - Integer


### ■ ResponsePasswordDTO
### Atributos
#### ● password - String


### ■ ResponseUserDTO
### Atributos
#### ● id - Long
#### ● username - String
#### ● email - String


### ■ UserDTO
### Atributos
#### ● id - Long
#### ● username - String
#### ● email - String
#### ● password - String
#### ● token - String

---------------------------------------------------------------------
## Repositories
### ■ UserRepository
#### Contém um método ```Boolean existsByEmail(String email)``` retorna um Boolean e recebe por parâmetro um email do tipo String,ou seja se for encontrado um usuário com um email que já existe,retorna true,senão retorna false


----------------------------------------------------------------------
## Services
### ■ UserService
### Propriedades (utilizo a anotação Autowired para injetar as dependências)
#### ● modelMapper (Mapeamento de entidade e DTO's)
#### ● repository (Acessar o repositório do usuário)

### Métodos
```
public Boolean existsByEmailService(String email){
    return repository.existsByEmail(email);
}
``` 
#### ↑ Retorna um Boolean se existir ou não um usuário com determinado email que é do tipo String e é passado por parâmetro.



```
public boolean existsUserById(Long id){
    return repository.existsById(id);
}
``` 
#### ↑ Retorna um Boolean se existir ou não um usuário com um determinado id que é do tipo Long passado por parâmetro.



```
public ResponseApiMessageStatus createUserService(UserDTO userDTO){
    boolean existsByEmail = existsByEmailService(userDTO.getEmail());
    if (existsByEmail){
        throw new DataIntegrityViolationException("Email já existe,tente outro por favor");
    }

    User userModel = modelMapper.map(userDTO, User.class);
    userModel.setUsername(userDTO.getUsername());
    userModel.setEmail(userDTO.getEmail());
    userModel.setPassword(userDTO.getPassword());
    repository.save(userModel);

    String message = "Usuário criado com sucesso!";
    Integer status = 201;
    return new ResponseApiMessageStatus(message,status);
} 
``` 
#### ↑ O método retorna um ResponseApiMessageStatus,sua finalidade é criar um usuário,recebe como parâmetro um UserDTO,é atribuido a um atributo do tipo boolean o método existsByEmailService,onde o email é pegoatravés do getEmail do userDTO trazido da requisição,é verificado se existsByEmail fo igual a true,será lançada uma exceção de DataIntegrityViolationException com uma mensagem personalizada.
#### ↑ É criado um atributo do tipo User que recebe um mapeamento de DTO para Entidade,é setado as propriedadesdas requisições como username,email e password,que são recuperadas através de userDTO.getNomeDaPropriedade,e chamo o método save() do JPARepository e a entidade é salva no banco de dados.
#### ↑ É criado dois atributos,um para mensagem e outro para status,ambos para sucesso do processo e personalizados,e tem como retorno uma nova instância de ResponseApiMessageStatus,que recebe por parâmetros os atributos message e status. 



```
public ResponseUserDTO findUserById(Long id){
    User userModel = repository.findById(id).orElseThrow(()-> new EntityNotFoundException("ID de usuário não encontrado"));
    
    ResponseUserDTO userDTO = new ResponseUserDTO();
    userDTO.setId(userModel.getId());
    userDTO.setUsername(userModel.getUsername());
    userDTO.setEmail(userModel.getEmail());
    
    return userDTO;
}
```
#### ↑ O método retorna ResponseUserDTO,sua finalidade é recuperar um usuário pelo seu id,recebe como parâmetro um id do tipo Long. Tem um atributo do tipo User que recupera um usuário pelo id do repository,e para evitar exceções do tipo NullPointerException,utilizo o método orElseThrow() para gerar uma exceção EntityNotFoundException com uma mensagem personalizada.   
#### ↑ É criado um atributo com retorno do tipo ResponseUserDTO que inicializa uma nova instância de ResponseUserDTO vazia,através desse atributo seto as propriedades id,username e email,que  são recuperadas pelo atributo userModel e inseridas no atributo userDTO,e por fim userDTO é retornada.  



```
public List<UserDTO> findAll(){
    List<User> userList=repository.findAll();
    List<UserDTO> userDTOList = new ArrayList<>();
    for (User user:userList){
        UserDTO userDTO=modelMapper.map(user,UserDTO.class);
        userDTOList.add(userDTO);
    }

    return userDTOList;
}
```
#### ↑ O método retorna uma lista de UserDTO,sua finalidade é recuperar todos usuários,até então somente para testes.
#### ↑ Tem dois atributos,ambos listas,o primeiro do tipo User na qual irá ser armazenado os usuários recuperados do banco através do repository eseu método findAll(),a segunda lista é do tipo UserDTO para armazenar os usuários que de entidade serão mapeados para DTO e começa como um ArrayList vazio.
#### ↑ É realizado um for do atributo userList que será armazenado cada usuário um atributo do tipo User,dentro do bloco for,tem um atributo do tipo UserDTO,que mapea os dados de entidade para DTO,e adiciona esse atributo na lista de DTO,fora do bloco for,é retornado a lista de DTO.



```
public ResponseApiMessageStatus updateUserByIdService(Long id,ResponseUserDTO userDTO){
    boolean existsUser = existsUserById(id);
    if(!existsUser){
        throw new EntityNotFoundException("Usuário não encontrado");
    }

    Optional<User> userModel = repository.findById(id);
    userModel.map(user -> {
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        repository.save(user);
        return user;
    });
    String message = "Usuário atualizado com sucesso";
    Integer status = 200;

    return new ResponseApiMessageStatus(message,status);
}
```
#### ↑ O método retorna ResponseApiMessageStatus,sua finalidade é alterar o username e/ou email de um determinado usuário pelo seu id,que tem como parâmetro um id com tipo Long,e um userDTO do tipo ResponseUserDTO.
#### ↑ Primeiramente tem um atributo do tipo boolean que recebe o método existsUserId que recebe o id como parâmetro,para verificar se o usuário existe realmente,senão existir,é lançada uma exceção do tipo EntityNotFoundException com uma mensagem personalizada.
#### ↑ Tem um atributo do tipo Optional<User> que facilita o uso do map e prepara caso o retorno seja nulo,o atributo recebe um usuário pelo seu id,logo abaixo,utilizo uma expressão lambda map no atributo setando 'user' como parâmetro,através dele seto username e email,os valores são recuperados através do atributo userDTO,chamo repository,e pelo seu método save,salvo a entidade user e a retorno.
#### ↑ Crio dois atributos personalizados,um é message do tipo String,e o outro é status do tipo Integer e retorno uma nova instância de ResponseApiMessageStatus que recebe message e status como parâmetros. 



```
public ResponseApiMessageStatus updatePasswordByIdService(Long id, ResponsePasswordDTO passwordDTO){
    boolean existsUser = existsUserById(id);
    if(!existsUser){
        throw new EntityNotFoundException("Usuário não encontrado");
    }

    Optional<User> userModel = repository.findById(id);
    userModel.map(user -> {
        user.setUsername(user.getUsername());
        user.setEmail(user.getEmail());
        user.setPassword(passwordDTO.getPassword());
        repository.save(user);
        return user;
    });

    String message = "Usuário atualizado com sucesso";
    Integer status = 200;

    return new ResponseApiMessageStatus(message,status);
}
```
#### ↑ O método retorna ResponseApiMessageStatus,sua finalidade é alterar a password de um determinado usuário pelo seu id,que tem como parâmetro um id com tipo Long,e um userDTO do tipo ResponsePasswordDTO.
#### ↑ Primeiramente tem um atributo do tipo boolean que recebe o método existsUserId que recebe o id como parâmetro,para verificar se o usuário existe realmente,senão existir,é lançada uma exceção do tipo EntityNotFoundException com uma mensagem personalizada.
#### ↑ Tem um atributo do tipo Optional<User> que facilita o uso do map e prepara caso o retorno seja nulo,o atributo recebe um usuário pelo seu id,logo abaixo,utilizo uma expressão lambda map no atributo setando 'user' como parâmetro,através dele seto username,email e password,os valores são recuperados através do atributo userDTO,chamo repository,e pelo seu método save,salvo a entidade user e a retorno.
#### ↑ Crio dois atributos personalizados,um é message do tipo String,e o outro é status do tipo Integer e retorno uma nova instância de ResponseApiMessageStatus que recebe message e status como parâmetros.



```
public void deleteUserByIdService(Long id){
    repository.deleteById(id);
}
```
#### ↑ O método tem o tipo de retorno vazio,ou seja,sem retorno,sua finalidade é deletar um usuário pelo seu id,tem como parâmetro um id do tipo Long
#### ↑ através do repository,acessa o tipo deleteById com o id como parâmetro

----------------------------------------------------------------------
## Controllers
### ■ UserController
#### Recebe duas anotações sob o UserController,o 'RestController' para representar um controller,o 'RequestMapping("/users")' que define uma rota até os métodos
#### Detém uma propriedade service do tipo UserService,na qual vem a partir de injeção de dependências

```
@PostMapping
public ResponseEntity<ResponseApiMessageStatus> createUser(@RequestBody UserDTO userDTO, UriComponentsBuilder uriBuilder){
    ResponseApiMessageStatus response = service.createUserService(userDTO);
    URI path = uriBuilder.path("/users/{id}").buildAndExpand(userDTO.getId()).toUri();

    return ResponseEntity.created(path).body(response);
}
```
#### ↑ O controlador para criar usuário utiliza anotação 'PostMapping',o retorno é do tipo ResponseEntity<ResponseApiMessageStatus>,recebe por parâmetro UserDTO acompanhado da anotação 'RequestBody' que diz que são requisições com corpo,e UriComponentBuilder que iremos utilizar para a determinar a path mais tarde.
#### ↑ Tem um atributo que recebe o retorno da service createUserService do tipo ResponseApiMessageStatus,que recebe por parãmetro userDTO 
#### ↑ Logo abaixo,tem um atributo path do tipo URI,que recebe o parâmetro uriBuilder acessando o método path definindo o caminho até o id,logo após defino o id recebendo-o através do parâmetro userDTO do método buildAndExpand e depois transformo em URI.
#### ↑ O retorno é ResponseEntity,que acessa created inserindo o atriuto path e depois acessa body inserindo o atributo response.



```
@GetMapping("/{id}")
public ResponseEntity<ResponseUserDTO> getUserById(@PathVariable Long id){
    ResponseUserDTO userById = service.findUserById(id);
    return ResponseEntity.ok(userById);
}
```
#### ↑
#### ↑
#### ↑
#### ↑