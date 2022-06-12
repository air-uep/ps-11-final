package pl.air.hr.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "employees")
@NoArgsConstructor @Getter @Setter
public class Employee {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 50)
	@Column(name = "first_name", nullable = false)
	private String firstName;

	@NotBlank
	@Size(max = 100)
	@Column(name = "last_name", nullable = false)
	private String lastName;

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "hire_date")
	private LocalDate hireDate;

	@NotNull
	@DecimalMin(value = "0.0", inclusive = false)
	@Digits(integer = 7, fraction = 2)
	private BigDecimal salary;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "dep_id")
	private Department department;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "pos_id")
	private Position position;

}
