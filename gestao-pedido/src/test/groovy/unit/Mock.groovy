package unit

import spock.lang.Specification

class Mock extends Specification {
    def "test"() {
        when:
        def a = 1 + 1
        then:
        a == 2
    }
}
