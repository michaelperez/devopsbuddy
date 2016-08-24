package com.devopsbuddy.test.integration;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.devopsbuddy.DevopsbuddyApplication;
import com.devopsbuddy.backend.persistence.domain.backend.Plan;
import com.devopsbuddy.backend.persistence.domain.backend.Role;
import com.devopsbuddy.backend.persistence.domain.backend.User;
import com.devopsbuddy.backend.persistence.domain.backend.UserRole;
import com.devopsbuddy.backend.persistence.repositories.PlanRepository;
import com.devopsbuddy.backend.persistence.repositories.RoleRepository;
import com.devopsbuddy.backend.persistence.repositories.UserRepository;
import com.devopsbuddy.enums.PlansEnum;
import com.devopsbuddy.enums.RolesEnum;
import com.devopsbuddy.utils.UsersUtils;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DevopsbuddyApplication.class)
public class RepositoriesIntegrationTest {

	@Autowired
	private PlanRepository planRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	
	@Before
	public void init() {
		Assert.assertNotNull(planRepository);
		Assert.assertNotNull(roleRepository);
		Assert.assertNotNull(userRepository);
	}
	
	@Test
	public void testCreateNewPlan() throws Exception {
		Plan basicPlan = createPlan(PlansEnum.BASIC);
		planRepository.save(basicPlan);
		Plan retrievedPlan = planRepository.findOne(PlansEnum.BASIC.getId());
		Assert.assertNotNull(retrievedPlan);
	}
	
	
	@Test
    public void testCreateNewRole() throws Exception {

        Role userRole  = createRole(RolesEnum.BASIC);
        roleRepository.save(userRole);

        Role retrievedRole = roleRepository.findOne(RolesEnum.BASIC.getId());
        Assert.assertNotNull(retrievedRole);
    }

    @Test
    public void createNewUser() throws Exception {

        Plan basicPlan = createPlan(PlansEnum.BASIC);
        planRepository.save(basicPlan);
        
        User basicUser = UsersUtils.createBasicUser();
        basicUser.setPlan(basicPlan);
        
        Role basicRole = createRole(RolesEnum.BASIC);
        Set<UserRole> userRoles = new HashSet<UserRole>();
        UserRole userRole = new UserRole(basicUser, basicRole);
        userRoles.add(userRole);
        
        /** VERY IMPORTANT TO ALWAYS ADD, NEVER SET DIRECTLY */
        basicUser.getUserRoles().addAll(userRoles);
        
        for (UserRole ur : userRoles) {
        	roleRepository.save(ur.getRole());
        }
        
        User newlyCreatedUser = userRepository.findOne(basicUser.getId());
        Assert.assertNotNull(newlyCreatedUser);
        Assert.assertTrue(newlyCreatedUser.getId() != 0);
        Assert.assertNotNull(newlyCreatedUser.getPlan());
        Assert.assertNotNull(newlyCreatedUser.getPlan().getId());
        Set<UserRole> newlyCreatedUserUserRoles = newlyCreatedUser.getUserRoles();
        for (UserRole ur : newlyCreatedUserUserRoles) {
            Assert.assertNotNull(ur.getRole());
            Assert.assertNotNull(ur.getRole().getId());
        }
        
        
        
    }
	
	
	/** private methods */
	private Plan createPlan(PlansEnum plansEnum) {
		return new Plan(plansEnum);
	}
	
	private Role createRole(RolesEnum rolesEnum) {
		return new Role(rolesEnum);
	}
	
//	private User createBasicUser() {
//		User user = new User();
//		user.setUsername("basicUser");
//		user.setPassword("secret");
//		user.setEmail("m@example.com");
//		user.setFirstName("Mike");
//		user.setLastName("Perez");
//		user.setPhoneNumber("123456789");
//		user.setCountry("USA");
//		user.setEnabled(true);
//		user.setDescription("test");
//		user.setProfileImageUrl("https://www.google.com/images/basicuser");
//		return user;
//	}
}
