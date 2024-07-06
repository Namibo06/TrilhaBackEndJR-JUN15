## Pré-Requisitos do projeto
#### **JRE**
#### **JDK na versão 8+**
#### **IDE Intellij(recomendado) ou qualquer outra IDE da sua escolha**
#### **SQLite**
#### **Insomnia ou Postman**


----------------------------------------------------------------------------------------------
## Passo a passo para ter acesso ao projeto
#### - **Abra o seu GitHub**
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
#### ● SpringBoot
#### ● SQLite
#### ● Railway
#### ● JUnit
#### ● Hateoas
#### ● Spring Security
#### ● JWT
#### ● Spring WebFlux
#### ● Lombok
#### ● Spring JPA
#### ● ModelMapper
#### ● Hibernate Community Dialects
#### ● SpringBoot DevTools
#### ● Spring Validation
#### ● Swagger


-----------------------------------------------------------------------------
## Documentação

### Documentação Swagger (link para acessar abaixo)
#### **https://trilhabackendjr-jun15-production-e24a.up.railway.app/swagger-ui/index.html#/**


<br>

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
│   │   │               │   └──Filter
│   │   │               ├── controllers/
│   │   │               │   └── LoginController
│   │   │               │   └── TaskController
│   │   │               │   └── UserController
│   │   │               ├── dtos/
│   │   │               │   └── LoginDTO
│   │   │               │   └── ResponseApiMessageStatus
│   │   │               │   └── ResponsePasswordDTO
│   │   │               │   └── ResponseTokenDTO
│   │   │               │   └── ResponseUserDTO
│   │   │               │   └── TaskDTO
│   │   │               │   └── UserDTO
│   │   │               ├── enums/
│   │   │               │   └── Status
│   │   │               ├── models/
│   │   │               │   └── Task
│   │   │               │   └── User
│   │   │               ├── repositories/
│   │   │               │   └── TaskRepository
│   │   │               │   └── UserRepository
│   │   │               ├── services/
│   │   │               │   └── LoginService
│   │   │               │   └── TaskService
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
### **• Configs**
#### Utilizei esta class para receber um "@Bean" da class ModelMapper, que serve para mapeamento de entidade para DTO ou vice versa,como Models são o que vão para o banco e o que retornam deles,o que vem por meio de requisição e o que é retornado é um DTO,e por isso deve-se ser convertidos.

<br><br>

### **• Filter**
#### **▪ doFilterInternal**
##### Na classe Filter estendo de OncePerRequestFilter,por meio da herança tive que implementar o método doFilterInternal,na qual tenho como parâmetros response,request e filterChain.
##### Inicializo um atributo do tipo String na qual pelo parâmetro request,recebo a requisição da URI,ou seja,aquele nome setado no controller,que vem logo depois do domínio padrão,logo verifico se a requisição da URI é igual a "/users" ou "/login",se for através do ```filterChain.doFilter(request,response);``` eu passo a requisição e a resposta para o próximo filtro na cadeia de filtros,e o ```return;``` paro a execução do método impedindo que qualquer linha abaixo seja executada]
##### Tenho uma variável token que recebe um método ```findToken(request,response)``` na qual será falado um pouco abaixo,mas passa a requisição e a resposta para o próximo filtro na cadeia de filtros

<br>

#### **▪ findToken**
##### Seu retorno é uma String,e seu parâmetro é uma request do tipo HttpServletRequest
##### Recupero do meu cabeçalho o meu token através de Authorization,e verifico se é nulo,se for lanço uma exceção com mensagem personalizada
##### Retorno o token,retirando através do método replace,somente o "Bearer" que fica no inicio do token,e assim retorno o token

-------------------------------------------------------------------
## Models
### **Tabela User - "tb_user"** 
### **▪ Atributos:** 
#### ● id - Long - Chave Primária
#### ● username - String - Não Nulo | Tamanho variável até 20 caracteres 
#### ● email - String - Não Nulo | Único | Tamanho variável até 120 caracteres
#### ● password - String - Não Nulo | Único | Tamanho variável até 100 caracteres
#### ● token - String

<br>

### **Tabela Task - "tb_task"**
### **▪ Atributos:**
#### ● id - Long - Chave Primária
#### ● title - String - Não Nulo | Tamanho variável até 150 caracteres
#### ● description - String 
#### ● status - Status - Não Nulo | Enumerated
#### ● userId - Long | Não Nulo


---------------------------------------------------------------------
## DTO's
### **ResponseApiMessageStatus**
### **▪ Atributos**
#### ● message - String
#### ● status - Integer

<br>

### **ResponsePasswordDTO**
### **▪ Atributos**
#### ● password - String

<br>

### **ResponseUserDTO**
### **▪ Atributos**
#### ● id - Long
#### ● username - String
#### ● email - String

<br>

### **UserDTO**
### **▪ Atributos**
#### ● id - Long
#### ● username - String
#### ● email - String
#### ● password - String
#### ● token - String

---------------------------------------------------------------------
## Enum
### **Status**
### **Propriedades:**
#### ● ATIVO
#### ● CONCLUIDO
#### ● FECHADO


---------------------------------------------------------------------
## Repositories
### **UserRepository**
#### ```Boolean existsByEmail(String email)``` retorna um Boolean,e recebe por parâmetro um email do tipo String,ou seja se for encontrado um usuário com um email que já existe,retorna true,senão retorna false
#### ```Optional<User> findByEmail(String email)``` retorna um Optional do Model User,e recebe por parâmetro um email do tipo String,aqui irá retornar um Usuário que será buscado por determinado email,para evitar NullPointExecption é sempre verificado se é nulo
#### ```Boolean existsByEmailAndPassword(String email,String password)``` retorna um Boolean,e recebe por parâmetro um email do tipo String e uma password do tipo String,verifica se existe um usuário com determinado email e senha,se ecntrado retorna true,senão umm false 


----------------------------------------------------------------------
## Services
### **UserService**
### **▪ Propriedades:** (utilizo a anotação Autowired para injetar as dependências)
#### ● modelMapper (Mapeamento de entidade e DTO's)
#### ● repository (Acessar o repositório do usuário)

<br><br>

### **▪ Métodos:**

<br>

#### **▪ existsByEmailService**
```
public Boolean existsByEmailService(String email){
    return repository.existsByEmail(email);
}
``` 
#### ↑ Retorna um Boolean se existir ou não um usuário com determinado email que é do tipo String e é passado por parâmetro.

<br><br>

#### ***▪ existsUserById***
```
public boolean existsUserById(Long id){
    return repository.existsById(id);
}
``` 
#### ↑ Retorna um Boolean se existir ou não um usuário com um determinado id que é do tipo Long passado por parâmetro.

<br><br>

#### ***▪ createUserService***
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

<br><br>

#### ***▪ findUserById***
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

<br><br>

#### ***▪ findAll***
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

<br><br>

#### ***▪ updateUserByIdService***
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

<br><br>

#### ***▪ updatePasswordByIdService***
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

<br><br>

#### ***▪ deleteUserByIdService***
```
public void deleteUserByIdService(Long id){
    repository.deleteById(id);
}
```
#### ↑ O método tem o tipo de retorno vazio,ou seja,sem retorno,sua finalidade é deletar um usuário pelo seu id,tem como parâmetro um id do tipo Long
#### ↑ através do repository,acessa o tipo deleteById com o id como parâmetro

<br><br><br>

### **LoginService**

<br><br><br>

### **TaskService**

----------------------------------------------------------------------
## Controllers
### ■ UserController
#### Recebe duas anotações sob o UserController,o 'RestController' para representar um controller,o 'RequestMapping("/users")' que define uma rota até os métodos
#### Detém uma propriedade service do tipo UserService,na qual vem a partir de injeção de dependências

<br><br>

#### ***▪ createUser***
```
@PostMapping
public ResponseEntity<ResponseApiMessageStatus> createUser(@RequestBody UserDTO userDTO, UriComponentsBuilder uriBuilder){
    ResponseApiMessageStatus response = service.createUserService(userDTO);
    URI path = uriBuilder.path("/users/{id}").buildAndExpand(userDTO.getId()).toUri();

    return ResponseEntity.created(path).body(response);
}
```
#### ↑ O controlador para criar usuário,utiliza a anotação 'PostMapping',o retorno é do tipo ResponseEntity<ResponseApiMessageStatus>,recebe por parâmetro UserDTO acompanhado da anotação 'RequestBody' que diz que são requisições com corpo,e UriComponentBuilder que iremos utilizar para a determinar a path mais tarde.
#### ↑ Tem um atributo que recebe o retorno da service createUserService do tipo ResponseApiMessageStatus,que recebe por parãmetro userDTO 
#### ↑ Logo abaixo,tem um atributo path do tipo URI,que recebe o parâmetro uriBuilder acessando o método path definindo o caminho até o id,logo após defino o id recebendo-o através do parâmetro userDTO do método buildAndExpand e depois transformo em URI.
#### ↑ O retorno é ResponseEntity,que acessa created inserindo o atriuto path e depois acessa body inserindo o atributo response.

<br><br>

#### ***▪ getUserById***
```
@GetMapping("/{id}")
public ResponseEntity<ResponseUserDTO> getUserById(@PathVariable Long id){
    ResponseUserDTO userById = service.findUserById(id);
    return ResponseEntity.ok(userById);
}
```
#### ↑ O controlador para buscar um usuário pelo id,utiliza a anotação 'GetMapping' com um id depois da barra na rota,o retorno é do tipo ResponseEntity<ResponseUserDTO>,recebe por parâmetro id do tipo Long através da anotação 'PathVariable'.
#### ↑ Tem um atributo userById do tipo ResponseUserDTO que recebe da service o método findUserById passando por parâmetro o id.
#### ↑ O retorno é ResponseEntity,que acessando ok passa o atributo userById.

<br><br>

#### ***▪ getAllUsers***
```
@GetMapping
public ResponseEntity<List<UserDTO>> getAllUsers(){
    List<UserDTO> userDTOList = service.findAll();
    return ResponseEntity.ok(userDTOList);
}
```
#### ↑ O controlador para buscar todos usuários,utiliza a anotação 'GetMapping',o retorno é do tipo ResponseEntity<List<UserDTO>>.
#### ↑ Tem um atributo userDTOList do tipo lista UserDTO que recebe da service pelo método findAll todos usuários.
#### ↑ O retorno é ResponseEntity,que acessando ok,passa o atributo userDTOList.

<br><br>

#### ***▪ updateUserById***
```
@PutMapping("/{id}")
public ResponseEntity<ResponseApiMessageStatus> updateUserById(@PathVariable Long id,@RequestBody ResponseUserDTO userDTO){
    ResponseApiMessageStatus response = service.updateUserByIdService(id,userDTO);

    return ResponseEntity.ok(response);
}
```
#### ↑ O controlador para atualizar o username e email de um usuário pelo id,utiliza a anotação 'PutMapping' definindo barra id na rota,o retorno é do tipo ResponseEntity<ResponseApiMessageStatus>,recebe como parâmetro id do tipo Long pela anotacao 'PathVariable',e userDTO do tipo ResponseUserDTO que utiliza a anotacao 'RequestBody' para captar os dados durante a requisição.
#### ↑ Tem um atributo response do tipo ResponseApiMessageStatus,que recebe da service,o método updateUserByIdService,com parâmetros id e userDTO.
#### ↑ O retorno é ResponsiveEntity,que ao acessar o método ok,passa o atributo response.

<br><br>

#### ***▪ updatePasswordById***
```
@PutMapping("updatePassword/{id}")
public ResponseEntity<ResponseApiMessageStatus> updatePasswordById(@PathVariable Long id, @RequestBody ResponsePasswordDTO passwordDTO){
    ResponseApiMessageStatus response = service.updatePasswordByIdService(id,passwordDTO);

    return ResponseEntity.ok(response);
}
```
#### ↑ O controlador para atualizar a password de um usuário pelo id,utiliza a anotação 'PutMapping' definindo barra updatePassword barra id na rota,o retorno é do tipo ResponseEntity<ResponseApiMessageStatus>,recebe como parâmetro id do tipo Long pela anotacao 'PathVariable',e passwordDTO do tipo ResponsePasswordDTO que utiliza a anotacao 'RequestBody' para captar os dados durante a requisição.
#### ↑ Tem um atributo response do tipo ResponseApiMessageStatus,que recebe da service,o método updatePasswordByIdService,com parâmetros id e passwordDTO.
#### ↑ O retorno é ResponsiveEntity,que ao acessar o método ok,passa o atributo response.

<br><br>

#### ***▪ deleteUseryId***
```
@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteUserById(@PathVariable Long id){
    service.deleteUserByIdService(id);
    return ResponseEntity.noContent().build();
}
```
#### ↑ O controlador para deletar um usuário pelo id,utiliza a anotação 'DeleteMapping' definindo barra id na rota,o retorno é do tipo ResponseEntity<Void>,recebe como parâmetro id do tipo Long pela anotacao 'PathVariable'.
#### ↑ Acessa o méotodo deleteUserByIdService da propriedade service vinda através da injeção de dependência,com id como parâmetro. 
#### ↑ O retorno é ResponseEntity,que é sem conteúdo,como é sem conteúdo tenho que acecssar noContent() e logo após build().

<br><br><br>

### ■ LoginController

<br><br><br>

### ■ TaskController