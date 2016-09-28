package main.com.burkia.server

/**
 * Created by dszcz_000 on 24.09.2016.
 */
@SpringBootApplication
open class Application {

    @Bean
    open fun init(repository: CustomerRepository) = CommandLineRunner {
        repository.save(Customer("Jack", "Bauer"))
        repository.save(Customer("Chloe", "O'Brian"))
        repository.save(Customer("Kim", "Bauer"))
        repository.save(Customer("David", "Palmer"))
        repository.save(Customer("Michelle", "Dessler"))
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}