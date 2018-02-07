package swagger.example

import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import swagger.SwaggerApiGroup

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@SwaggerApiGroup('Business')
@Api(value = 'store', description = 'store resource')
@Transactional(readOnly = true)
class StoreController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @ApiOperation(
            value = "List Stores",
            nickname = "/",
            produces = "application/json",
            consumes = "application/json",
            httpMethod = "GET",
            response = Store,
            responseContainer = 'array'
    )
    @ApiResponses([
            @ApiResponse(code = 405, message = "Method Not Allowed. Only GET is allowed"),
    ])
    @ApiImplicitParams([
            @ApiImplicitParam(name = 'offset',
                    value = 'Records to skip',
                    defaultValue = '0',
                    paramType = 'query',
                    dataType = 'int'),
            @ApiImplicitParam(name = 'max',
                    value = 'Max records to return',
                    defaultValue = '10',
                    paramType = 'query',
                    dataType = 'int'),
            @ApiImplicitParam(name = 'sort',
                    value = 'Field to sort by',
                    defaultValue = 'id',
                    paramType = 'query',
                    dataType = 'string'),
            @ApiImplicitParam(name = 'order',
                    value = 'Order to sort by',
                    defaultValue = 'asc',
                    paramType = 'query',
                    dataType = 'string')
    ])
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Store.list(params), model:[storeCount: Store.count()]
    }


    @ApiOperation(
            value = "Show Store",
            nickname = "/{id}",
            produces = "application/json",
            consumes = "application/json",
            httpMethod = "GET",
            response = Store
    )
    @ApiResponses([
            @ApiResponse(code = 405, message = "Method Not Allowed. Only GET is allowed"),
            @ApiResponse(code = 404, message = "Resource Not Found")
    ])
    @ApiImplicitParams([
            @ApiImplicitParam(name = 'id',
                    value = 'Resource id',
                    paramType = 'query',
                    dataType = 'string',
                    required = true)
    ])
    def show(Store store) {
        respond store
    }

    def create() {
        respond new Store(params)
    }

    @Transactional
    def save(Store store) {
        if (store == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (store.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond store.errors, view:'create'
            return
        }

        store.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'store.label', default: 'Store'), store.id])
                redirect store
            }
            '*' { respond store, [status: CREATED] }
        }
    }

    def edit(Store store) {
        respond store
    }

    @Transactional
    def update(Store store) {
        if (store == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (store.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond store.errors, view:'edit'
            return
        }

        store.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'store.label', default: 'Store'), store.id])
                redirect store
            }
            '*'{ respond store, [status: OK] }
        }
    }

    @Transactional
    def delete(Store store) {

        if (store == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        store.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'store.label', default: 'Store'), store.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'store.label', default: 'Store'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
