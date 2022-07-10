/**
 * @author : gandalf
 * @created : 2022-06-23
**/
package com.drools.example.config;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.kie.spring.KModuleBeanFactoryPostProcessor;
import org.kie.spring.annotations.KModuleAnnotationPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

@Configuration
public class DroolsAutoConfiguration {
	/*   */
	private static final String RULES_PATH = "rules/";

	public KieFileSystem kieFileSystem() throws IOException {
		KieFileSystem kieFileSystem = getKieServices().newKieFileSystem();
		for (Resource file : getRuleFiles()) {
			kieFileSystem
					.write(ResourceFactory
							.newClassPathResource(String.format("%s%s", RULES_PATH, file.getFilename(), "UTF-8")));
		}
		return kieFileSystem;
	}

	public KieContainer kieContainer() throws IOException {
		final KieRepository kieRepository = getKieServices().getRepository();
		kieRepository.addKieModule(new KieModule() {
			public ReleaseId getReleaseId() {
				return kieRepository.getDefaultReleaseId();
			}
		});

		KieBuilder kieBuilder = getKieServices().newKieBuilder(kieFileSystem());
		kieBuilder.buildAll();
		KieContainer kieContainer = getKieServices().newKieContainer(kieRepository.getDefaultReleaseId());
		return kieContainer;
	};

	private KieServices getKieServices() {
		return KieServices.Factory.get();
	}

	private Resource[] getRuleFiles() throws IOException {
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		return resourcePatternResolver.getResources(String.format("classpath*:%s**/**", RULES_PATH));
	}

	private KieBase kieBase() throws IOException {
		return kieContainer().getKieBase();
	}

	private KieSession kieSession() throws IOException {
		return kieContainer().newKieSession();
	}

	public static KModuleAnnotationPostProcessor kiePostProcessor() {
		KModuleAnnotationPostProcessor kModuleAnnotationPostProcessor = new KModuleAnnotationPostProcessor();
		return kModuleAnnotationPostProcessor;
	}

	public static KModuleBeanFactoryPostProcessor kieBeanFactoryPostProcessor() throws MalformedURLException {
		KModuleBeanFactoryPostProcessor kModuleAnnotationPostProcessor = new KModuleBeanFactoryPostProcessor(
				new URL("file://Users/gandalf/mycode/parkway/Oreo/poc/target/classes"));
		return kModuleAnnotationPostProcessor;
	}
}
