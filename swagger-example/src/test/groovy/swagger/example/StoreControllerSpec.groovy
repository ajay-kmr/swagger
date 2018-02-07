package swagger.example

import grails.test.mixin.*
import spock.lang.*

@TestFor(StoreController)
@Mock(Store)
class StoreControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null

        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
        assert false, "TODO: Provide a populateValidParams() implementation for this generated test suite"
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.storeList
            model.storeCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.store!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def store = new Store()
            store.validate()
            controller.save(store)

        then:"The create view is rendered again with the correct model"
            model.store!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            store = new Store(params)

            controller.save(store)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/store/show/1'
            controller.flash.message != null
            Store.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def store = new Store(params)
            controller.show(store)

        then:"A model is populated containing the domain instance"
            model.store == store
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def store = new Store(params)
            controller.edit(store)

        then:"A model is populated containing the domain instance"
            model.store == store
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/store/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def store = new Store()
            store.validate()
            controller.update(store)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.store == store

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            store = new Store(params).save(flush: true)
            controller.update(store)

        then:"A redirect is issued to the show action"
            store != null
            response.redirectedUrl == "/store/show/$store.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/store/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def store = new Store(params).save(flush: true)

        then:"It exists"
            Store.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(store)

        then:"The instance is deleted"
            Store.count() == 0
            response.redirectedUrl == '/store/index'
            flash.message != null
    }
}
