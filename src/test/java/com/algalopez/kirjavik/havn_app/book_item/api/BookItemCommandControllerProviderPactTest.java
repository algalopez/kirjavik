package com.algalopez.kirjavik.havn_app.book_item.api;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import com.algalopez.kirjavik.havn_app.book_item.application.add_book_item.AddBookItemActor;
import com.algalopez.kirjavik.havn_app.book_item.application.add_book_item.AddBookItemCommand;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import java.util.List;
import java.util.Optional;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

@QuarkusTest
@Provider("kirjavik-havn-provider")
@PactFolder("pact")
public class BookItemCommandControllerProviderPactTest {
  private static final String SCENARIO_ADD_BOOK_ITEM = "scenario_add_book_item";

  @ConfigProperty(name = "quarkus.http.test-port")
  int quarkusPort;

  @InjectMock AddBookItemActor addBookItemActor;

  @TestTemplate
  @ExtendWith(PactVerificationInvocationContextProvider.class)
  void pactVerificationTestTemplate(PactVerificationContext context) {
    context.verifyInteraction();
  }

  @BeforeEach
  void beforeEach(PactVerificationContext context) {
    context.setTarget(new HttpTestTarget("localhost", quarkusPort));

    var isScenarioMatchStateBlacklisted =
        Optional.of(context.getInteraction().getProviderStates()).orElseGet(List::of).stream()
            .anyMatch(state -> SCENARIO_ADD_BOOK_ITEM.equals(state.getName()));

    if (isScenarioMatchStateBlacklisted) {
      Mockito.doNothing().when(addBookItemActor).command(Mockito.any(AddBookItemCommand.class));
    }
  }

  @State(SCENARIO_ADD_BOOK_ITEM)
  void clearData() {
    // Already handled in beforeEach
  }
}
