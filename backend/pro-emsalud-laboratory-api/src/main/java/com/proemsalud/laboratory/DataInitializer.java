package com.proemsalud.laboratory;


import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.proemsalud.laboratory.backup.domain.BackupConfiguration;
import com.proemsalud.laboratory.backup.repository.IBackupConfigurationRepository;
import com.proemsalud.laboratory.test.parameter.domain.Parameter;
import com.proemsalud.laboratory.test.parameter.domain.ParameterOption;
import com.proemsalud.laboratory.test.parameter.enumerate.ExamType;
import com.proemsalud.laboratory.test.parameter.enumerate.InputType;
import com.proemsalud.laboratory.test.parameter.repository.IParameterRepository;
import com.proemsalud.laboratory.user.domain.Role;
import com.proemsalud.laboratory.user.domain.User;
import com.proemsalud.laboratory.user.repository.IRoleRepository;
import com.proemsalud.laboratory.user.repository.IUserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

	private final IRoleRepository roleRepository;
	private final IUserRepository userRepository;
	private final IBackupConfigurationRepository backupRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final IParameterRepository parameterRepository;

	@Override
	public void run(ApplicationArguments args) {

		Role admin = findOrCreateRole("ROLE_ADMIN");
		findOrCreateRole("ROLE_USER");

		if (userRepository.findByUsername("carlos").isEmpty()) {
			User u = new User();
			u.setUsername("carlos");
			u.setPassword(passwordEncoder.encode("kaeell23"));
			u.setRole(admin);
			u.setCreatedAt(Instant.now());
			u.setUpdatedAt(Instant.now());
			userRepository.save(u);
		}

		if (backupRepository.count() == 0) {
			BackupConfiguration c = new BackupConfiguration();
			c.setEnabled(true);
			c.setIntervalHours(6);
			c.setDailyTime(LocalTime.of(2, 0));
			c.setBackupDirectory("C:\\LabBackups");
			c.setRetentionDays(14);
			backupRepository.save(c);
		}

		if (parameterRepository.count() == 0) {
			initializeParameters();
		}
	}

	private Role findOrCreateRole(String authority) {
		return roleRepository.findByAuthority(authority).orElseGet(() -> {
			Role r = new Role();
			r.setAuthority(authority);
			return roleRepository.save(r);
		});
	}

	private void initializeParameters() {

		List<Parameter> parameters = new ArrayList<>();

		// =========================
		// UROANALISIS
		// =========================

		parameters.add(createParameter("Color", ExamType.URINALYSIS, InputType.SELECT, 1, "Amarillo", "Amarillo claro",
				"Ámbar", "Rojo", "Naranja", "Marrón", "Incoloro"));

		parameters.add(createParameter("Aspecto", ExamType.URINALYSIS, InputType.SELECT, 2, "Turbio",
				"Ligeramente turbio", "Límpido"));

		parameters.add(createParameter("Densidad", ExamType.URINALYSIS, InputType.SELECT, 3, "1,000", "1,005", "1,010",
				"1,015", "1,020", "1,025", "1,030"));

		parameters.add(createParameter("pH", ExamType.URINALYSIS, InputType.SELECT, 4, "5", "6", "6.5", "7", "7.5", "8", "9"));

		parameters.add(createParameter("Leucocitos", ExamType.URINALYSIS, InputType.RADIO, 5, "NEG", "+", "++", "+++"));

		parameters.add(createParameter("Nitrito", ExamType.URINALYSIS, InputType.SELECT, 6, "POSITIVO", "NEGATIVO"));

		parameters.add(
				createParameter("Urobilinógeno", ExamType.URINALYSIS, InputType.RADIO, 7, "NEG", "+", "++", "+++"));

		parameters.add(createParameter("Proteínas", ExamType.URINALYSIS, InputType.TEXT, 8));

		parameters.add(createParameter("Glucosa", ExamType.URINALYSIS, InputType.RADIO, 9, "NEG", "+", "++", "+++"));

		parameters.add(createParameter("Cetonas", ExamType.URINALYSIS, InputType.TEXT, 10));

		parameters.add(createParameter("Bilirrubinas", ExamType.URINALYSIS, InputType.TEXT, 11));

		parameters
				.add(createParameter("Eritrocitos", ExamType.URINALYSIS, InputType.RADIO, 12, "NEG", "+", "++", "+++"));

		// EXAMEN MICROSCOPICO

		parameters.add(createParameter("Leucocitos", ExamType.URINALYSIS, InputType.TEXT, 13));

		parameters.add(createParameter("Eritrocitos", ExamType.URINALYSIS, InputType.TEXT, 14));

		parameters.add(createParameter("Células epiteliales", ExamType.URINALYSIS, InputType.RADIO, 15, "NEG", "+",
				"++", "+++"));

		parameters.add(createParameter("Bacterias", ExamType.URINALYSIS, InputType.RADIO, 16, "NEG", "+", "++", "+++"));

		parameters.add(createParameter("Moco", ExamType.URINALYSIS, InputType.RADIO, 17, "NEG", "+", "++", "+++"));

		parameters.add(createParameter("Cristales", ExamType.URINALYSIS, InputType.TEXT, 18));

		parameters.add(createParameter("Levaduras", ExamType.URINALYSIS, InputType.RADIO, 19, "NEG", "+", "++", "+++"));

		parameters.add(createParameter("Otros", ExamType.URINALYSIS, InputType.TEXT, 20));

		// =========================
		// COPROANALISIS
		// =========================

		parameters.add(createParameter("Color", ExamType.STOOL, InputType.SELECT, 1, "Marrón", "Negro", "Rojo",
				"Blanco", "Amarillo", "Verde"));

		parameters.add(createParameter("Consistencia", ExamType.STOOL, InputType.SELECT, 2, "Pastoso", "Duro",
				"Diarreico", "Semi-diarreico"));

		parameters.add(createParameter("Moco", ExamType.STOOL, InputType.RADIO, 3, "NEG", "+", "++", "+++"));

		parameters.add(createParameter("pH", ExamType.STOOL, InputType.SELECT, 4, "1", "2", "3", "4", "5", "6", "7",
				"8", "9", "10", "11", "12"));

		parameters.add(createParameter("Sangre", ExamType.STOOL, InputType.RADIO, 5, "NEG", "+", "++", "+++"));

		parameters.add(
				createParameter("Restos alimenticios", ExamType.STOOL, InputType.RADIO, 6, "NEG", "+", "++", "+++"));

		// EXAMEN MICROSCOPICO

		parameters.add(createParameter("Leucocitos", ExamType.STOOL, InputType.RADIO, 7, "NEG", "+", "++", "+++"));

		parameters.add(createParameter("Eritrocitos", ExamType.STOOL, InputType.RADIO, 8, "NEG", "+", "++", "+++"));

		parameters.add(createParameter("Levaduras", ExamType.STOOL, InputType.RADIO, 9, "NEG", "+", "++", "+++"));

		parameters.add(
				createParameter("Células vegetales", ExamType.STOOL, InputType.RADIO, 10, "NEG", "+", "++", "+++"));

		parameters.add(
				createParameter("Fibras musculares", ExamType.STOOL, InputType.RADIO, 11, "NEG", "+", "++", "+++"));

		parameters.add(createParameter("Grasas", ExamType.STOOL, InputType.RADIO, 12, "NEG", "+", "++", "+++"));

		parameters.add(createParameter("Almidones", ExamType.STOOL, InputType.RADIO, 13, "NEG", "+", "++", "+++"));

		parameters.add(createParameter("Parásitos", ExamType.STOOL, InputType.TEXT, 14));

		parameters.add(createParameter("Bacterias", ExamType.STOOL, InputType.RADIO, 15, "NEG", "+", "++", "+++"));

		parameterRepository.saveAll(parameters);
	}

	private Parameter createParameter(String name, ExamType examType, InputType inputType, Integer displayOrder,
			String... options) {

		Parameter parameter = Parameter.builder().name(name).examType(examType).inputType(inputType)
				.displayOrder(displayOrder).build();

		for (int i = 0; i < options.length; i++) {

			ParameterOption option = ParameterOption.builder().value(options[i]).displayOrder(i + 1)
					.parameter(parameter).build();

			parameter.getOptions().add(option);
		}

		return parameter;
	}

}
