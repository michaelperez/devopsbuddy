package com.devopsbuddy.test.integration;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
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
import com.devopsbuddy.utils.UserUtils;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DevopsbuddyApplication.class)
public class UserIntegrationTest extends AbstractIntegrationTest {

	@Autowired
	private PlanRepository planRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Rule public TestName testName = new TestName();
	
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

    	String username = testName.getMethodName();
    	String email = testName.getMethodName() + "@devopsbuddy.com";
    	
//        Plan basicPlan = createPlan(PlansEnum.BASIC);
//        planRepository.save(basicPlan);
//        
    	
        User basicUser = UserUtils.createBasicUser(username, email);

//        basicUser.setPlan(basicPlan);
        
//        Role basicRole = createRole(RolesEnum.BASIC);
//        Set<UserRole> userRoles = new HashSet<UserRole>();
//        UserRole userRole = new UserRole(basicUser, basicRole);
//        userRoles.add(userRole);	
//        
//        /** VERY IMPORTANT TO ALWAYS ADD, NEVER SET DIRECTLY */
//        basicUser.getUserRoles().addAll(userRoles);
//        
//        for (UserRole ur : userRoles) {
//        	roleRepository.save(ur.getRole());
//        }
        
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

    @Test
    public void testDeleteUser() throws Exception {
    	
    	String username = testName.getMethodName();
    	String email = testName.getMethodName() + "@devopsbuddy.com";
    	
    	User basicUser = createUser(username, email);
    	userRepository.delete(basicUser.getId());
    }
	
	/** private methods */
//	protected Plan createPlan(PlansEnum plansEnum) {
//		return new Plan(plansEnum);
//	}
//	
//	protected Role createRole(RolesEnum rolesEnum) {
//		return new Role(rolesEnum);
//	}
//	
//	protected User createUser(String userName, String email) {
//		Plan basicPlan = createPlan(PlansEnum.BASIC);
//		planRepository.save(basicPlan);
//		
//		User basicUser = UserUtils.createBasicUser(userName, email);
//        basicUser.setPlan(basicPlan);
//        
//        Role basicRole = createRole(RolesEnum.BASIC);
//        roleRepository.save(basicRole);
//        
//        Set<UserRole> userRoles = new HashSet<UserRole>();
//        UserRole userRole = new UserRole(basicUser, basicRole);
//        userRoles.add(userRole);
//        
//        basicUser.getUserRoles().addAll(userRoles);
//        basicUser = userRepository.save(basicUser);
//        
//        return basicUser;        
//	}
}
