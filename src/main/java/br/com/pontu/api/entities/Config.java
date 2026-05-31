package br.com.pontu.api.entities;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import br.com.pontu.api.enums.DiscountType;
import br.com.pontu.api.enums.EmploymentType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.MapKeyEnumerated;
import lombok.Data;

@Data
@Entity(name = "tb_configs")
public class Config {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(precision = 13, scale = 2, nullable = false)
    private BigDecimal monthlySalary = BigDecimal.ZERO;

    @Column(nullable = false)
    private Double dailyWorkingHours = 8.0; 

    @Column(nullable = false)
    private LocalTime durationLunchBreak = LocalTime.of(1, 0);

    @Column(nullable = false)
    private boolean mandatoryLunch = true;

    @Column(nullable = false)
    private Double overtimePercentage = 50.0;

    @Column(nullable = false)
    private boolean flexibleInterval = false;

    @Column(nullable = false)
    private boolean additionalNocturnal = false;

    @Column(nullable = true)
    private Double additionalNocturnalPercentage = 20.0; 
    
    @Column(nullable = false)
    private LocalTime entryTime = LocalTime.of(8, 0); 
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EmploymentType employmentRelationship = EmploymentType.CLT;

    @Column(nullable = false)
    private boolean transportationVoucher = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true, length = 20)
    private DiscountType transportationDiscountType;

    @Column(precision = 10, scale = 2, nullable = true)
    private BigDecimal transportationValue;

    @Column(nullable = false)
    private boolean mealVoucher = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true, length = 20)
    private DiscountType mealVoucherDiscountType;

    @Column(precision = 10, scale = 2, nullable = true)
    private BigDecimal mealVoucherValue;

    @Column(nullable = false)
    private boolean taxEnabled = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true, length = 20)
    private DiscountType taxDiscountType;

    @Column(precision = 10, scale = 2, nullable = true)
    private BigDecimal taxValue;

    @Column(nullable = false)
    private boolean incomeTax = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true, length = 20)
    private DiscountType incomeTaxDiscountType;

    @Column(precision = 10, scale = 2, nullable = true)
    private BigDecimal incomeTaxValue;

    @Column(length = 100, nullable = true)
    private String city;

    @Column(length = 2, nullable = true)
    private String state;

    @ElementCollection
    @CollectionTable(name = "tb_user_daily_journey", joinColumns = @JoinColumn(name = "config_id"))
    @MapKeyColumn(name = "day_week")
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "hours_worked")
    private Map<DayOfWeek, Double> journeyPerDay = new HashMap<>();

    @ElementCollection
    @CollectionTable(name = "tb_user_holidays", joinColumns = @JoinColumn(name = "config_id"))
    @Column(name = "holiday_date")
    private Set<LocalDate> holidays = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "tb_user_compensations", joinColumns = @JoinColumn(name = "config_id"))
    @Column(name = "compensation_date")
    private Set<LocalDate> compensations = new HashSet<>();
}