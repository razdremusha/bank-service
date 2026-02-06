package org.example;

/**
 * Интерфейс для получения сценариев
 */
public interface ScenarioProvider {

  /**
   * Функция, которая получает сценарий, если он есть
   *
   * @return Полученный сценарий
   */
  Scenario tryGetScenario();
}
