package example;

import java.util.Collections;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.HibernateValidator;

public class HibernateValidatorExample {

	public static void main(String[] args) {
		final ValidatorFactory factory = Validation
				.byProvider(HibernateValidator.class)
				.providerResolver(() -> Collections.singletonList(new HibernateValidator()))
				.configure()
				.addMapping(ClassLoader.getSystemResourceAsStream("META-INF/constraints.xml"))
				.buildValidatorFactory();
		final Validator validator = factory.getValidator();

		final ConstrainedObject invalid = new ConstrainedObject("g");

		final Set<ConstraintViolation<ConstrainedObject>> violations = validator.validate(invalid);
		violations.forEach(violation -> {
			System.out.println("INVALID VALUE: " + violation.getInvalidValue());
		});


	}

}
