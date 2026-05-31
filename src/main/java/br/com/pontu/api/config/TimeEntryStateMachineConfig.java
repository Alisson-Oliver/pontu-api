package br.com.pontu.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import br.com.pontu.api.enums.TimeEntryState;
import br.com.pontu.api.enums.TimeEntryType;

@Configuration
@EnableStateMachineFactory
public class TimeEntryStateMachineConfig extends StateMachineConfigurerAdapter<TimeEntryState, TimeEntryType> {
    @Override
    public void configure(StateMachineStateConfigurer<TimeEntryState, TimeEntryType> states) throws Exception {
        states.withStates()
            .initial(TimeEntryState.INITIAL)
            .state(TimeEntryState.WORKING)
            .state(TimeEntryState.ON_LUNCH)
            .end(TimeEntryState.FINISHED);
    }   

    @Override
    public void configure(StateMachineTransitionConfigurer<TimeEntryState, TimeEntryType> transitions) throws Exception {
        transitions
            .withExternal()
                .source(TimeEntryState.INITIAL).target(TimeEntryState.WORKING).event(TimeEntryType.IN)
            .and()
            .withExternal()
                .source(TimeEntryState.WORKING).target(TimeEntryState.ON_LUNCH).event(TimeEntryType.LUNCH_START)
            .and()
            .withExternal()
                .source(TimeEntryState.ON_LUNCH).target(TimeEntryState.WORKING).event(TimeEntryType.LUNCH_END)
            .and()
            .withExternal()
                .source(TimeEntryState.WORKING).target(TimeEntryState.FINISHED).event(TimeEntryType.OUT);
    }
}
