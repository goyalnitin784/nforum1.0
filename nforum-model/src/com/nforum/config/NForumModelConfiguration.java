package com.nforum.config;



import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({
	"classpath:com/nforum/products/air/config/nforum_db.xml"
	})
public class NForumModelConfiguration {

}
