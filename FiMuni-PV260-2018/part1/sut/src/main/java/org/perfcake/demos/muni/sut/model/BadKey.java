package org.perfcake.demos.muni.sut.model;

import java.util.Objects;

/**
 * Created by pmacik on 21.4.16.
 */
public class BadKey {
   public final String key;

   public BadKey(String key) {
      this.key = key;
   }

   @Override
   public boolean equals(final Object o) {
      if (this == o) {
         return true;
      }
      if (o == null || getClass() != o.getClass()) {
         return false;
      }
      final BadKey badKey = (BadKey) o;
      return Objects.equals(key, badKey.key);
   }

   @Override
   public int hashCode() {

      return Objects.hash(key);
   }
}
