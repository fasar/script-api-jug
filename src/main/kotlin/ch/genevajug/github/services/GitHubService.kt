package ch.genevajug.github.services

import ch.genevajug.github.model.Contributor
import ch.genevajug.github.model.PagesBuildRes
import ch.genevajug.github.model.Repository
import ch.genevajug.github.model.UserRes
import feign.Param
import feign.RequestLine
import org.springframework.cache.annotation.Cacheable


interface GitHubService {


    @Cacheable("github")
    @RequestLine("GET /users/{username}/repos?sort=full_name")
    fun repos(@Param("username") owner: String): List<Repository>

    @Cacheable("github")
    @RequestLine("GET /repos/{owner}/{repo}/contributors")
    fun contributors(@Param("owner") owner: String, @Param("repo") repo: String): List<Contributor>

    @RequestLine("GET /user")
    @Cacheable("github")
    fun getUser(): UserRes

    @RequestLine("GET /repos/{owner}/{repo}/pages/builds")
    fun buildPagesStatus(@Param("owner") owner: String, @Param("repo") repo: String): Array<PagesBuildRes>


}