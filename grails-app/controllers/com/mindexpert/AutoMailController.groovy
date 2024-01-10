package com.mindexpert

import grails.rest.*
import grails.converters.*
import grails.plugins.mail.MailService

class AutoMailController extends RestfulController {
    static responseFormats = ['json', 'xml']

    AutoMailController() {
        super(AutoMail)
    }

    MailService mailService

    def sendMultipleEmails() {

        def requestData = request.JSON
        String tos = ""+requestData.to
        String subjects = ""+requestData.subject
        String messagesCount = ""+requestData.count 
        def msgCount = messagesCount as Integer
        

        String filePath = "./src/main/webapp/msg/messages.txt" //text file path 
        List<String> messages = readMessagesFromFile(filePath)

        def successResponse = [:]

        // Send 5 emails with different messages
        for (int i = 1; i<=msgCount; i++) {
            String bodys = messages[i]
            // Use a different variable name to avoid conflict 
            String emailTo = tos
            String forSubjects = subjects+i
             // Introduce a 2-second delay
            Thread.sleep(2000)

            mailService.sendMail {
                to emailTo
                subject forSubjects
                body bodys 
            }
            // Inside your loop
             successResponse["message"] = "Email ${i + 1} sent successfully."
        }

      // After the loop
      successResponse["message"] = "Multiple emails sent successfully."
        render successResponse as JSON
    }

     private List<String> readMessagesFromFile(String filePath) {
        try {
            return new File(filePath).readLines()
        } catch (Exception e) {
            log.error("Error reading messages from file: ${e.message}")
            return []
        }
    }
}