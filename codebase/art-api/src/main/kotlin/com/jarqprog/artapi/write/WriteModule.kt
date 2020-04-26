package com.jarqprog.artapi.write

import com.jarqprog.artapi.write.api.WriteFacade
import com.jarqprog.artapi.write.api.WritePlugin
import com.jarqprog.artapi.write.api.art.ArtRepository
import com.jarqprog.artapi.write.api.artjson.ArtJsonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class WriteModule {

    @Bean
    fun writeFacade(@Autowired artRepository: ArtRepository,
                    @Autowired jsonRepository: ArtJsonRepository): WriteFacade {

        return WritePlugin(artRepository, jsonRepository)
    }
}