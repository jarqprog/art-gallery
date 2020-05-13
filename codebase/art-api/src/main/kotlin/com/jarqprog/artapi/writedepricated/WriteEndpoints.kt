package com.jarqprog.artapi.writedepricated


import com.jarqprog.artapi.writedepricated.api.WriteFacade
import com.jarqprog.artapi.writedepricated.dto.ArtDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

//@RestController
//@RequestMapping("/api/art")
//class WriteEndpoints(@Autowired private val facade: WriteFacade) {
//
//    @PostMapping
//    fun save(@RequestBody art: ArtDTO) {
//        facade.saveOne(art)
//    }
//}