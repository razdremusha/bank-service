package org.example;

/**
 * Интерфейс для сценариев
 */
public interface Scenario {

  /**
   * Функция получает имя сценария
   *
   * @return Имя
   */
  String getName();

  /**
   * Функция, приводящая сценарий в работу
   */
  void run();
}
