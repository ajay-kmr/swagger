package swagger.example

class Book {

    String title
    String isbn



    static constraints = {
        title blank: false
        isbn nullable: true
    }


}
