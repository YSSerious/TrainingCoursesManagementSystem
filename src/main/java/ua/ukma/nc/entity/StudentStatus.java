package ua.ukma.nc.entity;

import java.io.Serializable;

/**
 * Created by Алексей on 30.10.2016.
 */
public class StudentStatus implements Serializable {

      private User student;
      private Status status;

      public StudentStatus() {
      }

      public StudentStatus(User student, Status status) {
            this.student = student;
            this.status = status;
      }

      public User getStudent() {
            return student;
      }

      public void setStudent(User student) {
            this.student = student;
      }

      public Status getStatus() {
            return status;
      }

      public void setStatus(Status status) {
            this.status = status;
      }

      @Override
      public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            StudentStatus that = (StudentStatus) o;

            if (student != null ? !student.equals(that.student) : that.student != null) return false;
            return status != null ? status.equals(that.status) : that.status == null;

      }

      @Override
      public int hashCode() {
            int result = student != null ? student.hashCode() : 0;
            result = 31 * result + (status != null ? status.hashCode() : 0);
            return result;
      }

      @Override
      public String toString() {
            return "StudentStatus{" +
                    "student=" + student +
                    ", status=" + status +
                    '}';
      }
}
