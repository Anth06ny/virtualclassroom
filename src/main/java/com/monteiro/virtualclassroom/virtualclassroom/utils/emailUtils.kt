package com.monteiro.virtualclassroom.virtualclassroom.utils

import com.sun.mail.smtp.SMTPTransport
//import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException
import java.util.*
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.Session
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

const val DEST_MAIL = "contacts@amonteiro.fr"
const val PORT_NUMBER = "587"
const val MAIL_HOST = "ssl0.ovh.net"
const val MAIL_SUBJECT = "Message laissé sur le site"
const val MAIL_LOGIN = "contacts"
const val MAIL_PWD = ""

fun main() {
    sendEmail("anth06ny@gmail.com", "test")
}


fun sendEmail(from: String, message: String) {

    // Get system properties
    val properties = System.getProperties()

    // Setup mail server
    properties["mail.smtp.host"] = "mail.amonteiro.fr"
    properties["mail.smtp.port"] = PORT_NUMBER
    //properties["mail.smtp.ssl.enable"] = "true"
    properties["mail.smtp.auth"] = "true"

    val session = Session.getInstance(properties, null)
    val msg = MimeMessage(session)

    try {

        // from
        msg.setFrom(InternetAddress(from))

        // to
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(DEST_MAIL, false))

        // subject
        msg.subject = MAIL_SUBJECT

        // content
        msg.setText(message)

        msg.sentDate = Date()

        // Get SMTPTransport
        val t = session.getTransport("smtp") as SMTPTransport

        // connect
        t.connect("mail.amonteiro.fr", MAIL_LOGIN, MAIL_PWD)

        // send
        t.sendMessage(msg, msg.allRecipients)

        println("Response: " + t.lastServerResponse)

        t.close()

    } catch (e: MessagingException) {
        e.printStackTrace()
    }


}