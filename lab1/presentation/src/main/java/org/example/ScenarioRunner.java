package org.example;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Класс, который показывает возможные сценарии
 */
public class ScenarioRunner {

  /**
   * Все возможные сценарии
   */
  private Collection<ScenarioProvider> providers;

  /**
   * Конструктор раннера сценариев
   *
   * @param providers Все возможные сценарии
   */
  public ScenarioRunner(Collection<ScenarioProvider> providers) {
    this.providers = providers;
  }

  /**
   * Функция, реализующая выбор сценария в консоли
   */
  public void run() {

    Collection<Scenario> scenarios = getScenarios();
    System.out.println("Options:");
    for (Scenario scenario : scenarios) {
      System.out.println("--" + ">" + scenario.getName() + "<--");
    }
    System.out.println("Enter scenario name:");
    String scenarioName = System.console().readLine();
    for (Scenario scenario : scenarios) {
      if (scenario.getName().equals(scenarioName)) {
        scenario.run();
        break;
      }
    }
  }

  /**
   * Функция, получащая все сценарии
   *
   * @return Все возможные сценарии
   */
  private Collection<Scenario> getScenarios() {
    Collection<Scenario> scenariosResult = new ArrayList<>();
    for (ScenarioProvider provider : providers) {
      Scenario result = provider.tryGetScenario();
      if (result != null) {
        scenariosResult.add(result);
      }
    }
    return scenariosResult;
  }

}
