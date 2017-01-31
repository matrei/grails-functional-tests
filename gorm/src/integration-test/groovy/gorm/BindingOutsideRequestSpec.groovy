package gorm

/**
 * Created by graemerocher on 31/01/2017.
 */
import grails.test.mixin.integration.Integration
import grails.transaction.*
import grails.util.GrailsWebMockUtil
import spock.lang.*

@Integration
@Rollback
class BindingOutsideRequestSpec extends Specification {

    void "Save nested company without webrequest (Quartz/Bpotstrap)"() {
        given:
        def company = new Company(relation: new CompanyRelation(address: new RelationAddress()))
        when:
        company.validate()
        then:
        !company.errors.hasErrors()
        when:
        company.save()
        then:
        !company.errors.hasErrors()
    }
    void "Save nested company nested with webrequest"() {
        given:
        GrailsWebMockUtil.bindMockWebRequest()
        def company = new Company(relation: new CompanyRelation(address: new RelationAddress()))
        when:
        company.validate()
        then:
        !company.hasErrors()
        when:
        company.save()
        then:
        !company.hasErrors()
    }
}
