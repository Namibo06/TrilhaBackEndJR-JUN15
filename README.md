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
#### ● userId - Long | Não Nulo | Faz referência ao id da tabela User


---------------------------------------------------------------------
## DTO's

### **LoginDTO**
### **▪ Atributos**
#### ● email - String
#### ● password - String

<br>

### **ResponseApiMessageStatus**
### **▪ Atributos**
#### ● message - String
#### ● status - Integer

<br>

### **ResponsePasswordDTO**
### **▪ Atributos**
#### ● password - String

<br>

### **ResponseTokenDTO**
### **▪ Atributos**
#### ● token - String
#### ● message - String
#### ● status - Integer

<br>

### **ResponseUserDTO**
### **▪ Atributos**
#### ● id - Long
#### ● username - String
#### ● email - String

<br>

### **TaskDTO**
### **▪ Atributos**
#### ● id - Long
#### ● title - String
#### ● description - String
#### ● status - Status
#### ● userId - Long

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

### **TaskRepository**
#### Somente é utilizado métodos já criados pelo JPARepository

----------------------------------------------------------------------
## Services
### **UserService**
### **▪ Propriedades:** (utilizo a anotação Autowired para injetar as dependências)
#### ● modelMapper (Mapeamento de entidade e DTO's)
#### ● repository (Acessa o repositório do usuário)

<br><br>

### **▪ Métodos:**

<br>

#### **▪ existsByEmailService**
```
public Boolean existsByEmailService(String email){
    return repository.existsByEmail(email);
}
``` 
#### ↑ Retorna um Boolean se existir ou não um usuário com determinado email que é do tipo String e é passado por argumento.

<br><br>

#### ***▪ existsUserById***
```
public boolean existsUserById(Long id){
    return repository.existsById(id);
}
``` 
#### ↑ Retorna um Boolean se existir ou não um usuário com um determinado id que é do tipo Long passado por argumento.

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
#### ↑ O método retorna um ResponseApiMessageStatus,sua finalidade é criar um usuário,recebe como parâmetro um UserDTO
#### ↑ É atribuido a uma variável do tipo boolean o método existsByEmailService,onde o email é recuperado através do getEmail do userDTO trazido da requisição,é verificado se existsByEmail fo igual a true,será lançada uma exceção de DataIntegrityViolationException com uma mensagem personalizada.
#### ↑ É criado uma variável do tipo User que recebe um mapeamento de DTO para Entidade,é setado as propriedades das requisições como username,email e password,que são recuperadas através de userDTO.getNomeDaPropriedade,e chamo o método save() do JPARepository e a entidade é salva no banco de dados.
#### ↑ É criado duas variáveis,um para message e outro para status,ambos para sucesso do processo e personalizados,e tem como retorno uma nova instância de ResponseApiMessageStatus,que recebe por argumentos os atributos message e status. 

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
#### ↑ O método retorna ResponseUserDTO,sua finalidade é recuperar um usuário pelo seu id,recebe como parâmetro um id do tipo Long.
#### ↑ Tem uma variável do tipo User que recupera um usuário pelo id do repository,e para evitar exceções do tipo NullPointerException,utilizo o método orElseThrow() para gerar uma exceção EntityNotFoundException com uma mensagem personalizada.   
#### ↑ É criado uma variável com retorno do tipo ResponseUserDTO que inicializa uma nova instância de ResponseUserDTO vazia,através desse atributo seto as propriedades id,username e email,que  são recuperadas pelo atributo userModel e inseridas no atributo userDTO,e por fim userDTO é retornada.  

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
#### ↑ Tem duas variáveis,ambos listas,a primeira do tipo User na qual irá ser armazenado os usuários recuperados do banco através do repository eseu método findAll(),a segunda lista é do tipo UserDTO para armazenar os usuários que de entidade serão mapeados para DTO e começa como um ArrayList vazio.
#### ↑ É realizado um for na variável userList que será armazenado cada usuário em uma variável do tipo User,dentro do bloco for,tem um variável do tipo UserDTO,que mapea os dados de entidade para DTO,e adiciona essa variável na lista de DTO,fora do bloco for,é retornado a lista de DTO.

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
#### ↑ Tem uma variável do tipo boolean que recebe o método existsUserId que recebe o id como argumento,para verificar se o usuário existe realmente,senão existir,é lançada uma exceção do tipo EntityNotFoundException com uma mensagem personalizada.
#### ↑ Tem uma variável do tipo Optional<User> que facilita o uso do map e prepara caso o retorno seja nulo,a variável recebe um usuário pelo seu id,logo abaixo,utilizo uma expressão lambda map na variável setando 'user' como parâmetro,através dele seto username e email,os valores são recuperados através do atributo userDTO,chamo repository,e pelo seu método save,salvo a entidade user e a retorno.
#### ↑ Crio duas variáveis personalizadas,um é message do tipo String,e o outro é status do tipo Integer e retorno uma nova instância de ResponseApiMessageStatus que recebe message e status como parâmetros. 

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
#### ↑ Primeiramente tem uma variável do tipo boolean que recebe o método existsUserId que recebe o id como argumento,para verificar se o usuário existe realmente,senão existir,é lançada uma exceção do tipo EntityNotFoundException com uma mensagem personalizada.
#### ↑ Tem uma variável do tipo Optional<User> que facilita o uso do map e prepara caso o retorno seja nulo,a variável recebe um usuário pelo seu id,logo abaixo,utilizo uma expressão lambda map no atributo setando 'user' como parâmetro,através dele seto username,email e password,os valores são recuperados através do atributo userDTO,chamo repository,e pelo seu método save,salvo a entidade user e a retorno.
#### ↑ Crio duas variáveis personalizadas,um é message do tipo String,e o outro é status do tipo Integer e retorno uma nova instância de ResponseApiMessageStatus que recebe message e status como parâmetros.

<br><br>

#### ***▪ deleteUserByIdService***
```
public void deleteUserByIdService(Long id){
    repository.deleteById(id);
}
```
#### ↑ O método tem o tipo de retorno vazio,ou seja,sem retorno,sua finalidade é deletar um usuário pelo seu id,tem como parâmetro um id do tipo Long
#### ↑ através do repository,acessa o tipo deleteById com o id como argumento

<br><br><br>

### **LoginService**
### **▪ Propriedades:** 
#### Sem propriedades

<br><br>

### **▪ Métodos:**

<br>

#### **▪ createToken**
```
public String createToken(){
    try{
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + 3600 *  1000);
        Algorithm algorithm = Algorithm.HMAC256("CodigoCertoTrilhaBackEndJr");

        return JWT.create()
                .withIssuer("Codigo Certo")
                .withExpiresAt(expirationDate)
                .sign(algorithm);
    }catch (JWTCreationException e){
        throw new RuntimeException("Falha ao criar token");
    }
}
```
#### ↑ Tem como retorno uma String,não possui parâmetros
#### ↑ Possui um try/catch,na qual tenta o primeiro bloco,caso não vá,cai no catch
#### ↑ No try,é recuperado o horário atual na variável now do tipo Date,a variável expirationDate do tipo Date,cria uma nova data pegando a hora atual de 'now' e adiciona mais 1 hora em cima,a variável algorithm é responsável por definir o tipo de assinatura do Algorithm a partir da minha secret,e abaixo retorno um token criado setando a assinatura,a data de expiração do token e o emissor do token   
#### ↑ No catch o tipo a ser verificado é JWTCreationException,se for,tenho como variavel 'e' que irá armazenar a mensagem de erro,porém não é usada,lanço uma exceção RuntimeException com uma mensagem personalizada

<br>

#### **▪ verifyToken**
```
public void verifyToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256("CodigoCertoTrilhaBackEndJr");
            JWTVerifier verifier=JWT.require(algorithm)
                    .withIssuer("Codigo Certo")
                    .build();

            DecodedJWT decodedJWT = verifier.verify(token);

        } catch (SignatureVerificationException e) {
            throw new RuntimeException("Assinatura do token inválida", e);
        } catch (JWTVerificationException e){
            throw new JWTVerificationException("Falha ao autenticar token: ",e);
        }
    }
```
#### ↑ Não possui retorno,e recebe um token como parâmetro do tipo String
#### ↑ Possui um try/catch,na qual tenta o primeiro bloco,caso não vá,cai no catch
#### ↑ No try,é setado o algorithm da mesma forma que está na parte de criação do token,tem uma variável verifier do tipo JWTVerifier que verifica o algoritmo,o emissor e builda,logo abaixo tenho uma variável decodedJWT do tipo DecodedJTW na qual recebe a variável verifier e por um método verify,verifica se o token ainda está válido  
#### ↑ Caso não fique no try,tenho dois catch's,o primeiro é do tipo SignatureVerificationException,e lanço uma exceção RuntimeException com mensagem personalizada com a variavel 'e' que captura o erro,e a segunda é do tipo JWTVerificationException na qual lanço uma exceção JWTVerificationException com uma mensagem personalizada com a variável 'e' qeu captura o erro do catch


<br><br><br>

### **TaskService**
### **▪ Propriedades:** (utilizo a anotação Autowired para injetar as dependências)
#### ● modelMapper (Mapeamento de entidade e DTO's)
#### ● repository (Acessa o repositório da tarefa)
#### ● userService (Acessa o serviço do usuário)

<br>

#### ***▪ createTaskService***
```
public ResponseApiMessageStatus createTaskService(TaskDTO taskDTO){
    boolean existsUserId=userService.existsUserById(taskDTO.getUserId());
    if (!existsUserId){
        throw new EntityNotFoundException("Usuario não encontrado");
    }

    Task taskModel = modelMapper.map(taskDTO, Task.class);
    repository.save(taskModel);

    String message="Tarefa criada com sucesso";
    Integer status=201;
    return new ResponseApiMessageStatus(message,status);
}
```
#### ↑ O retorno é do tipo ResponseApiMessageStatus e recebe por parâmetro um taskDTO do tipo TaskDTO 
#### ↑ Tem uma variável existsUserById do tipo boolean que verifica se existe o id do usuário dentro do serviço do usuário através do método existsUserById,e verifica num if depois se retorna um false,caso retorne lança uma exceção EntityNotFoundException com uma mensagem personalizada,caso seja true,segue o fluxo sem entrar no if 
#### ↑ Tem uma variável taskModel do tipo Task que recebe um modelMapper para fazer um mapeamento de TaskDTO para Task(Model) e salva taskModel no banco de dados,através do método save da depêndencia repository 
#### ↑ Tem uma variável message do tipo String já inicializado,e um status do tipo Integer já inicializado,e um retorno de uma nova instância de ResponseApiMessageStatus setando a message e status

<br><br>

#### ***▪ findAllTasksService***
```
public Page<TaskDTO> findAllTasksService(Pageable pageable){
    return repository
            .findAll(pageable)
            .map(task -> modelMapper.map(task,TaskDTO.class));
}
```
#### ↑ O retorno é do tipo Page<TaskDTO> e recebe por parâmetro um pageable do tipo Pageable
#### ↑ Retorno direto do repository,o método findAll que recebe por argumento o pageable,e percorre por um map,tendo como parâmetro task,fazendo um mapeamento de task para TaskDTO

<br><br>

#### ***▪ findTaskByIdService***
```
public EntityModel<TaskDTO> findTaskByIdService(Long id){
    Task taskModel = repository.findById(id).orElseThrow(()-> new EntityNotFoundException("Tarefa não encontrada"));
    TaskDTO taskDTO= modelMapper.map(taskModel, TaskDTO.class);

    return EntityModel.of(taskDTO,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TaskController.class).findById(id)).withSelfRel());
}
```
#### ↑ O retorno é do tipo EntityModel<TaskDTO> e recebe por parâmetro um di do tipo Long
#### ↑ Tem uma variável taskModel do tipo Task,que recebe repository,acessando seu método findById com o argumento id,e para proteger de voltar uma exceção NullPointerException,logo após o findById acesso mais um método orElseThrow que é um método da classe Optional,lançando um EntityNotFoundException com uma mensagem personalizada
#### ↑ Tem uma variável taskDTO do tipo TaskDTO que faz um mapeamento de taskModel para TaskDTO
#### ↑ O retorno é acessando o método of de EntityModel,passando por argumento,taskDTO e é adicionado um link através de linkTo do WebMvcLinkBuilder,em ```WebMvcLinkBuilder.methodOn(TaskController.class)``` é criado um proxy para simular uma chamada ao método controlador sem realmente executá-lo, ```findById``` o proxy chama o método findById no controller passando id como argumento,não executa o método mas registra as informações para construir o link,```withSelfRel()```  adiciona o link,que é apontado para o próprio recurso

<br><br>

#### ***▪ updateTaskByIdService***
```
public ResponseApiMessageStatus updateTaskByIdService(Long id,TaskDTO taskDTO){
    boolean existsUserId=userService.existsUserById(taskDTO.getUserId());

    if (!existsUserId){
        throw new EntityNotFoundException("Usuário não encontrado");
    }

    Task taskModel = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada"));
    taskModel.setTitle(taskDTO.getTitle());
    taskModel.setDescription(taskDTO.getDescription());
    taskModel.setStatus(taskDTO.getStatus());
    repository.save(taskModel);

    String message="Tarefa atualizada com sucesso";
    Integer status=200;
    return new ResponseApiMessageStatus(message,status);
}
```
#### ↑ O retorno é do tipo ResponseApiMessageStatus,que recebe como parâmetro id do tipo Long,e taskDTO do tipo TaskDTO
#### ↑ Tem uma variável existsUserId do tipo boolean,que recebe userService,acessando o mmétodo existsUserById,recebendo de argumento,taskDTO acessando o userId,e depois é verificada se o retorno for false,é lançada uma exceção EntityNotFoundException com uma mensagem personalizada  
#### ↑ Tem uma variável taskModel do tipo Task ,que recebe repository acessando o método findById,passando id como argumento,logo após acessando orElseThrow da classe Optional,para se proteger de NullPointerException
#### ↑ setando os atributos na variável taskModel através do método acessor setter,passando como argumentos,o métodos acessores getters de taskDTO,e por último salvando atráves do método save de repository,a taskModel 
#### ↑ Tenho duas variáveis,a primeira sendo message do tipo String e a segundo o statusdo tipo Integer,por último retorno uma nova instância de ResponseApiMessageStatus,passando por argumento,message e status 

<br><br>

#### ***▪ deleteTaskByIdService***
```
public void deleteTaskByIdService(Long id){
    repository.deleteById(id);
}
```
#### ↑ Não tem retorno,e recebe como parâmetro id do tipo Long
#### ↑ Acessa o método deleteById do repository,e passa como argumento o id


----------------------------------------------------------------------
## Controllers
### ■ UserController
#### Recebe duas anotações sob o UserController,o 'RestController' para representar um controller,o 'RequestMapping("/users")' que define uma rota específica inicial para acessar o controller
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
#### ↑ O método para criar usuário,utiliza a anotação 'PostMapping',o retorno é do tipo ResponseEntity<ResponseApiMessageStatus>,recebe por parâmetro UserDTO acompanhado da anotação 'RequestBody' que diz que são requisições com corpo,e UriComponentBuilder que iremos utilizar para a determinar a path mais tarde.
#### ↑ Tem uma variável que recebe o retorno da service createUserService do tipo ResponseApiMessageStatus,que recebe por argumento userDTO 
#### ↑ Logo abaixo,tem uma variável path do tipo URI,que recebe o parâmetro uriBuilder acessando o método path definindo o caminho até o id,logo após defino o id recebendo-o através do argumento userDTO do método buildAndExpand e depois transformo em URI.
#### ↑ O retorno é ResponseEntity,que acessa created inserindo o atriuto path e depois acessa body inserindo a variável response.

<br><br>

#### ***▪ getUserById***
```
@GetMapping("/{id}")
public ResponseEntity<ResponseUserDTO> getUserById(@PathVariable Long id){
    ResponseUserDTO userById = service.findUserById(id);
    return ResponseEntity.ok(userById);
}
```
#### ↑ O método para buscar um usuário pelo id,utiliza a anotação 'GetMapping' com um id depois da barra na rota,o retorno é do tipo ResponseEntity<ResponseUserDTO>,recebe por parâmetro id do tipo Long através da anotação 'PathVariable'.
#### ↑ Tem uma variável userById do tipo ResponseUserDTO que recebe da service o método findUserById passando por argumento o id.
#### ↑ O retorno é ResponseEntity,que acessando o método ok,que passa a variável userById.

<br><br>

#### ***▪ getAllUsers***
```
@GetMapping
public ResponseEntity<List<UserDTO>> getAllUsers(){
    List<UserDTO> userDTOList = service.findAll();
    return ResponseEntity.ok(userDTOList);
}
```
#### ↑ O método para buscar todos usuários,utiliza a anotação 'GetMapping',o retorno é do tipo ResponseEntity<List<UserDTO>>.
#### ↑ Tem uma variável userDTOList do tipo lista UserDTO que recebe da service pelo método findAll todos usuários.
#### ↑ O retorno é ResponseEntity,que acessando ok,passa a variável userDTOList.

<br><br>

#### ***▪ updateUserById***
```
@PutMapping("/{id}")
public ResponseEntity<ResponseApiMessageStatus> updateUserById(@PathVariable Long id,@RequestBody ResponseUserDTO userDTO){
    ResponseApiMessageStatus response = service.updateUserByIdService(id,userDTO);

    return ResponseEntity.ok(response);
}
```
#### ↑ O método para atualizar o username e email de um usuário pelo id,utiliza a anotação 'PutMapping' definindo barra id na rota,o retorno é do tipo ResponseEntity<ResponseApiMessageStatus>,recebe como parâmetro id do tipo Long pela anotacao 'PathVariable',e userDTO do tipo ResponseUserDTO que utiliza a anotacao 'RequestBody' para captar os dados durante a requisição.
#### ↑ Tem uma variável response do tipo ResponseApiMessageStatus,que recebe da service,o método updateUserByIdService,com argumentos id e userDTO.
#### ↑ O retorno é ResponsiveEntity,que ao acessar o método ok,passa a variável response.

<br><br>

#### ***▪ updatePasswordById***
```
@PutMapping("updatePassword/{id}")
public ResponseEntity<ResponseApiMessageStatus> updatePasswordById(@PathVariable Long id, @RequestBody ResponsePasswordDTO passwordDTO){
    ResponseApiMessageStatus response = service.updatePasswordByIdService(id,passwordDTO);

    return ResponseEntity.ok(response);
}
```
#### ↑ O método para atualizar a password de um usuário pelo id,utiliza a anotação 'PutMapping' definindo barra updatePassword barra id na rota,o retorno é do tipo ResponseEntity<ResponseApiMessageStatus>,recebe como parâmetro id do tipo Long pela anotacao 'PathVariable',e passwordDTO do tipo ResponsePasswordDTO que utiliza a anotacao 'RequestBody' para captar os dados durante a requisição.
#### ↑ Tem uma variável response do tipo ResponseApiMessageStatus,que recebe da service,o método updatePasswordByIdService,com argumentos id e passwordDTO.
#### ↑ O retorno é ResponsiveEntity,que ao acessar o método ok,passa a variável response.

<br><br>

#### ***▪ deleteUseryId***
```
@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteUserById(@PathVariable Long id){
    service.deleteUserByIdService(id);
    return ResponseEntity.noContent().build();
}
```
#### ↑ O método para deletar um usuário pelo id,utiliza a anotação 'DeleteMapping' definindo barra id na rota,o retorno é do tipo ResponseEntity<Void>,recebe como parâmetro id do tipo Long pela anotacao 'PathVariable'.
#### ↑ Acessa o método deleteUserByIdService da propriedade service vinda através da injeção de dependência,com id como argumento. 
#### ↑ O retorno é ResponseEntity,que é sem conteúdo,como é sem conteúdo tenho que acecssar noContent() e logo após build().

<br><br><br>

### ■ LoginController
#### Recebe duas anotações sob o LoginController,o 'RestController' para representar um controller,o 'RequestMapping("/login")' que define uma rota específica inicial para acessar o controller
#### Detém duas propriedades,a primeira loginService do tipo LoginService,e a segunda userService do tipo UserService,na qual vem a partir de injeção de dependências

<br><br>

#### ***▪ login***
```
public ResponseEntity<ResponseTokenDTO> login(@RequestBody LoginDTO userDTO){
    try{
        Boolean existsUser = userService.authenticateUser(userDTO);
        if(!existsUser){
            throw new EntityNotFoundException("Usuário não existe");
        }
        String tokenCreated = loginService.createToken();
        ResponseApiMessageStatus updateUserTokenById = userService.updateTokenById(userDTO.getEmail(),tokenCreated);
        String MESSAGE_OK = updateUserTokenById.getMessage();
        Integer STATUS_OK = updateUserTokenById.getStatus();
        ResponseTokenDTO response = new ResponseTokenDTO(tokenCreated,MESSAGE_OK,STATUS_OK);

        return ResponseEntity.ok(response);
    }catch (BadCredentialsException e){
        throw new BadCredentialsException("Houve alguma falha ao autenticar usuário: "+e);
    }catch (Exception e){
        throw new InternalException("Erro interno: "+e);
    }
}
```
#### ↑ O método login,utiliza a anotação 'PostMapping',o retorno é do tipo ResponseEntity<ResponseTokenDTO>,recebe por parâmetro userDTO do tipo LoginDTO acompanhado da anotação 'RequestBody' que diz que são requisições com corpo.
#### ↑ Dentro do bloco try,tem seis variáveis
#### ↑ A primeira variável é existsUser do tipo boolean,que recebe userService acessando o método authenticateUser,passando por argumento userDTO,se o retorno da variável for false,será lançada uma exceção EntityNotFoundException com uma mensagem personalizada
#### ↑ A segunda variável é a tokenCreated do tipo String,que recebe loginService acessando o método createToken
#### ↑ A terceira variável é a updateUserTokenById do tipo ResponseApiMessageStatus que recebe userService acessando o método updateTokenById,passando por argumento o email vindo de userDTO,e o token de tokenCreated
#### ↑ A quarta variável é a MESSAGE_OK do tipo String que recebe updateUserTokenById,através do método acessor getter,recupera a message
#### ↑ A quinta variável é STATUS_OK que recebe updateUserTokenById,através do método acessor getter,recupera o status
#### ↑ A sexta é response do tipo ResponseTokenDTO na qual recebe uma nova instância de ResponseTokenDTO passando o tokenCreated,MESSAGE_OK e STATUS_OK como argumento
#### ↑ É retornado um ResponseEntity,acessando o método ok,passando response como argumento
#### ↑ Tem dois catch's,o primeiro é do tipo BadCredentialsException,que é lançado uma exceção do tipo BadCredentialsException,com uma mensagem personalizada junto com a variável do parâmetro,e o segundo uma Exception generalista na qual é lançada uma InternalException passando uma mensagem personalizada junto com a variável do parâmetro  

<br>

#### ***▪ verifyToken***
```
public ResponseEntity<ResponseApiMessageStatus> verifyToken(@PathVariable String token){
    loginService.verifyToken(token);

    String message="Token verificado com sucesso!";
    Integer status = 200;
    ResponseApiMessageStatus response = new ResponseApiMessageStatus(message,status);

    return ResponseEntity.ok(response);
}
```
#### ↑ O método verifyToken,utiliza a anotação 'PostMapping',o retorno é tod tipo ResponseEntity<ResponseApiMessageStatus>,recebe por parâmetro um token do tipo String e recebida através da url pela anotação 'PathVariable'
#### ↑ Acessa o método verifyToken do loginService,e passa como argumento o parâmetro token
#### ↑ Tem três variáveis,message do tipo String,status do tipo Integer,e response do tipo ResponseApiMessageStatus que recebe uma nova instância de ResponseApiMessageStatus,passando message e status como argumentos
#### ↑ O retorno é do tipo ResponseEntity,acessando o método ok,e passando a variável response como argumento 

<br><br><br>

### ■ TaskController
#### Recebe duas anotações sob o TaskController,o 'RestController' para representar um controller,o 'RequestMapping("/tasks")' que define uma rota específica inicial para acessar o controller
#### Detém uma propriedade service do tipo TaskService,na qual vem a partir de injeção de dependências

<br><br>

#### ***▪ createTask***
```
public ResponseEntity<ResponseApiMessageStatus> createTask(@RequestBody @Valid TaskDTO taskDTO, UriComponentsBuilder uriBuilder){
    ResponseApiMessageStatus response = service.createTaskService(taskDTO);
    URI path = uriBuilder.path("/tasks/{id}").buildAndExpand(taskDTO.getId()).toUri();

    return ResponseEntity.created(path).body(response);
}
```
#### ↑ O método createTask,utiliza a anotação 'PostMapping',o retorno é do tipo ResponseEntity<ResponseApiMessageStatus>,recebe dois parâmtros,sendo o primeiro taskDTO do tipo TaskDTO acompanhado das anotações 'RequestBody' (recuperar valores das requisições) e 'Valid' (validar conforme no DTO),e uriBuilder do tipo UriComponentsBuilder para path posteriormente
#### ↑ Tem uma variável response do tipo ResponseApiMessageStatus,que recebe o método createTaskService,vindo da service,que passa taskDTO como argumento
#### ↑ Tem uma variável path do tipo URI,que recebe o parâmetro uriBuilder,e acessa seu método path passando um caminho como argumento,acessando o método buildAndExpand passando como parâmetro o id vindo de taskDTO,e por último é transformado em URI através do método toUri
#### ↑ O retorno  é do ResponseEntity,acessando o método created passando como argumento a variável path,depois acessa o método body passando a variável response como argumento

<br><br>

#### ***▪ findAllTasks***
```
public ResponseEntity<Page<TaskDTO>> findAllTasks(@PageableDefault(size = 15) Pageable pageable){
    Page<TaskDTO> pageableTasks =  service.findAllTasksService(pageable);

    return ResponseEntity.ok(pageableTasks);
}
```
#### ↑ O método findAllTasks,utiliza a anotação 'GetMapping',o retorno é do tipo ResponseEntity<Page<TaskDTO>>,recebe um parâmetro,um pageable do tipo Pageable,ainda com a anotação 'PageableDefault' setando o tamanho em 15,ou seja,o retorno é limitado até 15 registros
#### ↑ Tem uma variável pageableTasks do tipo Page<TaskDTO>,que recebe findAllTaskService do service,com pageable como argumento
#### ↑ O retorno é ResponseEntity,acessando o método ok,passando como argumento pageableTasks

<br><br>

#### ***▪ findTaskById***
```
public ResponseEntity<EntityModel<TaskDTO>> findTaskById(@PathVariable Long id) {
    EntityModel<TaskDTO> entityModel = service.findTaskByIdService(id);

    return ResponseEntity.ok(entityModel);
}
```
#### ↑ O método findTaskById,utiliza a anotação 'GetMapping',o retorno é do tipo ResponseEntity<EntityModel<TaskDTO>>,recebe um parâmetro,um id do tipo Long e uma anotação 'PathVariable' na qual espera um id na url 
#### ↑ Tem uma variável entityModel do tipo EntityModel<TaskDTO>,que recebe findTaskByIdService de service,e passa id como argumento
#### ↑ O retorno é ResponseEntity,que acessa o método ok,e passa entityModel como argumento

<br><br>

#### ***▪ updateTaskById***
```
public ResponseEntity<ResponseApiMessageStatus> updateTaskById(@PathVariable Long id,@RequestBody TaskDTO taskDTO){
    ResponseApiMessageStatus response = service.updateTaskByIdService(id,taskDTO);

    return ResponseEntity.ok(response);
}
```
#### ↑ O método updateTaskById,utiliza a anotação 'PutMapping',o retorno é do tipo ResponseEntity<ResponseApiMessageStatus>,recebe dois parâmetros,o primeiro sendo um id do tipo Long e uma anotação 'PathVariable' na qual espera um id na url, e o segundo um taskDTO do tipo TaskDTO junto da anotação 'RequestBody' para receber os valores das requisições
#### ↑ Tem uma variável response do tipo ResponseApiMessageStatus,que recebe updateTaskByIdService da service,e passa id e taskDTO como argumentos
#### ↑ O retorno é ResponseEntity,que acessa o método ok,e passa response como argumento

<br><br>

#### ***▪ deleteTaskById***
```
public ResponseEntity<Void> deleteTaskById(@PathVariable Long id){
    service.deleteTaskByIdService(id);

    return ResponseEntity.noContent().build();
}
```
#### ↑ O método deleteTaskById,utiliza a anotação 'DeleteMapping',o retorno é do tipo ResponseEntity<Void>,recebe um parâmetro,sendo ele um id do tipo Long e uma anotação 'PathVariable' na qual espera um id na url
#### ↑ Acessa deleteTaskByIdService da service,e passa id como argumento 
#### ↑ O retorno é ResponseEntity,que acessa noContent e depois um build

--------------------------------------------------------------------------------------

#### Obs: Não foi conectado o SQLite local com o SQLite em produção,ou seja,o banco local somente irá puxar as requisições para o "localhost:8080/...",enquanto o banco em produção somente irá puxar os dados das requisições feitas para "https://trilhabackendjr-jun15-production-e24a.up.railway.app/..." 