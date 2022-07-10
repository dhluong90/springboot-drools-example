/**
 * @author : gandalf
 * @created : 2022-06-22
**/
package com.drools.example.domains;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Specialty { 
  
  private Long id;
  private String name;

}
