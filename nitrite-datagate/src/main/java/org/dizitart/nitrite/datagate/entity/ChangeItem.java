/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.entity;

import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author tareq
 */
@Getter
@Setter
@NoArgsConstructor
public class ChangeItem {

  private String nitriteId;
  private long timestamp;
  private Map<String, Object> changes = new LinkedHashMap<>();
}
