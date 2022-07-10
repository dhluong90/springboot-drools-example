/**
 * @author : gandalf
 * @created : 2022-06-22
**/
package com.drools.example.domains;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Hospital {

  private String name;
  private Long id;
  
}

