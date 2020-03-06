package com.monteiro.virtualclassroom.virtualclassroom.Controller;

import com.monteiro.virtualclassroom.virtualclassroom.model.bean.Classroom;
import com.monteiro.virtualclassroom.virtualclassroom.model.bean.Information;
import com.monteiro.virtualclassroom.virtualclassroom.model.dao.InformationDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@Controller
public class InformationController {
    @PostMapping("/addInformation")
    public String createInformation(@RequestParam String infoName, String infoLink, HttpSession session) throws IOException, SQLException {
        Classroom classroom = (Classroom) session.getAttribute("classroom");
        Information newInfo = new Information(infoName, infoLink, classroom);
        if (!infoName.equals("") && !infoLink.equals("")) {
            InformationDao.saveInformation(newInfo);
        }
        return "redirect:/adminConnected";
    }

    @PostMapping("/deleteInfo")
    public String deleteInformation(int infoDelete) throws IOException, SQLException {
        Information info = InformationDao.getInformation(infoDelete);
        InformationDao.deleteInformation(info);
        return "redirect:/adminConnected";
    }

    @RequestMapping(value = "/updateInformation")
    public String updateInformationFromList(int idInfo, String newInfoName, String newInfoUrl) throws IOException, SQLException {
        System.out.println(newInfoName);
        Information theInformation = InformationDao.getInformation(idInfo);
        String oldInfoValue = theInformation.getInformation_label();
        System.out.println(oldInfoValue);
        String oldInfoUrl = theInformation.getInformation_url();
        System.out.println(oldInfoUrl);

        if ((newInfoName == oldInfoValue) && (newInfoUrl == oldInfoUrl)) {
            System.out.println("missing");
            return "redirect:/adminConnected";
        } else if (newInfoName.isEmpty() && newInfoUrl.isEmpty()) {
            return "redirect:/adminConnected";
        } else if (newInfoName != oldInfoValue) {
            System.out.println(" name reussi");
            InformationDao.updateInformation("information_label", oldInfoValue, newInfoName);
            if (newInfoUrl != oldInfoUrl) {
                System.out.println("url reussi");
                InformationDao.updateInformation("information_url", oldInfoUrl, newInfoUrl);
                return "redirect:/adminConnected";
            }
            return "redirect:/adminConnected";
        }
        return "redirect:/adminConnected";
    }
}
