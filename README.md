# Card Payment Service

Простое Spring Boot приложение, эмулирующее обработку онлайн платежа соответствующим платежным сервисом, демонстрирует особенности жизненного цикла Spring бина.  

- подключение нового платежного сервиса в runtime с регистрацией через REST-контроллер  
- динамическая загрузка Java-класса из произвольного места файловой системы и создание бина Spring  
- выполнение default autowired метода интерфейса у бинов, реализующих этот интерфейс  

&nbsp;Интерес представляет интерфейс CardPaymentService, который в дефолтном autowired методе регистрирует себя в соответствующем контроллере.  
Таким образом, все Spring бины, имплементирующие этот интерфейс, при создании регистрируют себя в контроллере.  

Далее, в приложении есть выделенный контроллер, предназначенный для регистрации новых сервисов, реализующих интерфейс CardPaymentService.  
В соотвествующий endpoint контроллер принимает описание сервиса в виде BeanDefinitionMetaData, динамически загружает скомпилированный код
платежного сервиса из файловой системы и регистрирует Spring-бин.
  
При создании нового бина так же, как и в случае, уже имеющихся сервисов, происходит его регистрация в контроллере платежей.  
В примере скомпилированный класс нового сервиса MirCardPaymentService находится в каталоге resources/new_service, но ничего не мешает загружать его из любого места файловой системы.  

#### Как запустить приложение 
- клонировать проект в локальный git-репозиторий  
- импортировать проект в IntelliJ Idea  
- запустить PaymentApplication  
- выполнить запрос к приложению с помощью подходящего инструмента (curl, Insomnia и т.п.)  
` curl -d '{"paymentServiceName": "visaCardPaymentService","cardNumber":"4089987638761029","cardHolder":"John Smith","paymentReceiver":"ebay","amount":24356.78}' -H "Content-Type: application/json" -X POST http://localhost:8080/payment
 `  
 или  
 `
 curl -d '{"paymentServiceName": "masterCardPaymentService","cardNumber":"7569987638761029","cardHolder":"Robert Cray","paymentReceiver":"amazon","amount":98672.12}' -H "Content-Type: application/json" -X POST http://localhost:8080/payment
 `  
 получим что-то типа  
 `
 {  
   "operationResult": true  
 }  
 `  
 Далее, необходимо зарегистрировать новый сервис:  
 Передаем в приложение имя бина, full-qualified class name и путь в файловой системе к скомпилированному .class файлу нового сервиса:  
 `
 curl -d '{"beanName":"mirCardPaymentService","beanClassName":"org.javatraining.payment.service.MirCardPaymentService","beanLocation":"src/main/resources/new_service/"}' -H "Content-Type: application/json" -X POST http://localhost:8080/beanreg
 `  
 В ответ получим  
 `registered`  
 
 Затем, можно обратиться к этому зарегистрированному сервису:  
 `
 curl -d '{"paymentServiceName": "mirCardPaymentService","cardNumber":"8792647638761029","cardHolder":"Barbara Brown","paymentReceiver":"ebay","amount":782.65}' -H "Content-Type: application/json" -X POST http://localhost:8080/payment
 `
 
 В консоли приложения можно увидеть сообщение:    
 `
 MirCardPaymentService started    
 Payment service name: mirCardPaymentService  
 Card number: 8792647638761029  
 Card holder name: Barbara Brown  
 Receiver: ebay  
 Amount: 782.65  
 `   
 
 Таким образом, мы в runtime подключили новый платежный сервис и провели с его помощью платеж.  
 