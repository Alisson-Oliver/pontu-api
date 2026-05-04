package br.com.pontu.api.entities;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import br.com.pontu.api.enums.EmploymentType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.MapKeyEnumerated;

@Entity(name = "tb_configs")
public class Config {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private Double monthlySalary;

    @Column()
    private Double dailyWorkingHours; 

    @Column()
    private Double minLunchBreak;

    @Column()
    private Double maxLunchBreak;

    
    @Column()
    private Double entryTime;
    
    @Column()
    private EmploymentType employmentRelationship;

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
