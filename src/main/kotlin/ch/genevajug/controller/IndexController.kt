package ch.genevajug.controller

import ch.genevajub.config.model.MyConfig
import ch.genevajug.github.services.GitHubService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.ModelAndView


@Controller
class IndexController {

    @Autowired
    lateinit var githubService: GitHubService

    @Autowired
    lateinit var myConfig: MyConfig

    @GetMapping(path = ["/", "/index"])
    fun index(): String {
        return "index"
    }

    @GetMapping(path = ["/github"])
    fun github(): ModelAndView {
        val res = ModelAndView("github")
        res.model.put("guser", githubService.getUser())
        res.model.put("gpages", githubService.buildPagesStatus(myConfig.github.owner, myConfig.github.repo))
        return res
    }


}