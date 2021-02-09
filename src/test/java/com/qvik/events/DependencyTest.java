package com.qvik.events;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packagesOf = EventsApplication.class)
public class DependencyTest {

	private static final String MODULES = "com.qvik.events.modules..";
	private static final String EVENT = "..modules.event..";
	private static final String EXHIBITOR = "..modules.exhibitor..";
	private static final String PRESENTER = "..modules.presenter..";
	private static final String RESTAURANT = "..modules.restaurant..";
	private static final String STAGE = "..modules.stage..";
	private static final String VENUE = "..modules.venue..";
	private static final String CONTROLLER = "com.qvik.events.web..";
	private static final String MAIN = "com.qvik.events..";

	/* All Entities are accessed by CONTROLLER and MAIN */

	@ArchTest
	ArchRule modulesRule = classes().that().resideInAPackage(MODULES).should().onlyBeAccessed().byAnyPackage(MODULES,
			CONTROLLER, MAIN);

	@ArchTest
	ArchRule eventRule = classes().that().resideInAPackage(EVENT).should().onlyBeAccessed().byAnyPackage(EXHIBITOR,
			PRESENTER, RESTAURANT, STAGE, VENUE, CONTROLLER, MAIN);

	@ArchTest
	ArchRule exhibitorRule = classes().that().resideInAPackage(EXHIBITOR).should().onlyBeAccessed().byAnyPackage(EVENT,
			CONTROLLER, MAIN);

	@ArchTest
	ArchRule presenterRule = classes().that().resideInAPackage(PRESENTER).should().onlyBeAccessed().byAnyPackage(EVENT,
			CONTROLLER, MAIN);

	@ArchTest
	ArchRule restaurantRule = classes().that().resideInAPackage(RESTAURANT).should().onlyBeAccessed()
			.byAnyPackage(EVENT, VENUE, CONTROLLER, MAIN);

	@ArchTest
	ArchRule stageRule = classes().that().resideInAPackage(STAGE).should().onlyBeAccessed().byAnyPackage(EVENT, VENUE,
			CONTROLLER, MAIN);

	@ArchTest
	ArchRule venueRule = classes().that().resideInAPackage(VENUE).should().onlyBeAccessed().byAnyPackage(EVENT, STAGE,
			RESTAURANT, CONTROLLER, MAIN);

}
