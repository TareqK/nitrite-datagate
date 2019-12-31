/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.entity;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * A Class representing a list of changeItems
 *
 * @author tareq
 */
@Getter
@Setter
public class ChangeList {

  private List<ChangeItem> changeItems = new ArrayList<>();
  private String collection;
}
