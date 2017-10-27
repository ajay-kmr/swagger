import grails.util.BuildSettings
import grails.util.Environment

// See http://logback.qos.ch/manual/groovy.html for details on configuration
appender('STDOUT', ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%level %logger - %msg%n"
    }
}

root(INFO, ['STDOUT'])

def targetDir = BuildSettings.TARGET_DIR
if (Environment.isDevelopmentMode() && targetDir) {
    appender("FULL_STACKTRACE", FileAppender) {
        file = "${targetDir}/stacktrace.log"
        append = true
        encoder(PatternLayoutEncoder) {
            pattern = "%level %logger - %msg%n"
        }
    }
    logger("StackTrace", ERROR, ['FULL_STACKTRACE'], false)
}

loggerList = ['STDOUT']

logger('grails.app.controllers', DEBUG, loggerList, false)
logger('grails.app.services', DEBUG, loggerList, false)
logger('grails.app.jobs', DEBUG, loggerList, false)
logger('grails.app.domain', DEBUG, loggerList, false)
logger('grails.app.taglibs', DEBUG, loggerList, false)

logger('grails.app.init.swagger.example.BootStrap', DEBUG, loggerList, false)
logger('swagger.example.BootStrap', DEBUG, loggerList, false)
//logger('grails.app.controllers.com.construction.de.prp.audit.AuditInterceptor', TRACE, loggerList, false)

// hibernate SQL and bind variable tracking
logger('org.hibernate.SQL', DEBUG, ['STDOUT', 'ROLLING_SQL'], false)    // show sql statements
logger('org.hibernate.type.descriptor.sql.BasicBinder', TRACE, ['ROLLING_SQL'], false)
// show sql bind variable values

logger('org.apache.http.headers', INFO, loggerList, false)
//    logger('org.apache.http.wire', DEBUG, loggerList, false)
