// https://codeforces.com/contest/115/problem/A
package lab1;

import java.util.ArrayList;
import java.util.Scanner;

public class P115A {

    static public void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        int numberOfEmployees = scanner.nextInt();
        int[] input = new int[numberOfEmployees];
        for(int i=0; i<numberOfEmployees; i++){
            input[i] = scanner.nextInt();
        }

        EmployeeList list = new EmployeeList(numberOfEmployees);
        list.fill(input);
        System.out.print(list.findHeightOfMaxTree());
    }
}

class EmployeeList {

    public EmployeeList(int length){
        allocatingEmployees(length);
    }

    public void fill(int[] superiors){
        for(int i=0; i<superiors.length; i++){
            if(superiors[i] != -1){
                employees[i].setIsRoot(false);
                int superiorIndex = superiors[i]-1;
                employees[superiorIndex].addInferior(i+1);
            }
        }
    }

    public int findHeightOfMaxTree(){
        int maxHeight = 1;
        for(Employee employee: employees){
            if(employee.getIsRoot()) {
                int height = employee.calculateTreeHeight(this);
                if (height > maxHeight) {
                    maxHeight = height;
                }
            }
        }
        return maxHeight;
    }

    public Employee getEmployee(int number){
        return employees[number - 1];
    }


    void allocatingEmployees(int length){
        employees = new Employee[length];
        for(int i=0; i<length; i++){
            employees[i] = new Employee();
        }
    }

    static Employee[] employees;
}

class Employee {

    public void addInferior(int number){
        inferiors.add(number);
    }

    public void setIsRoot(boolean flag){
        isRoot = flag;
    }

    public boolean getIsRoot(){
        return isRoot;
    }

    public int calculateTreeHeight(EmployeeList list){
        if(inferiors.size() == 0){
            return 1;
        } else {
            int maxSubtreeHeight = 1;
            for(int employeeNumber: inferiors) {
                Employee inferior = list.getEmployee(employeeNumber);
                int subtreeHeight = inferior.calculateTreeHeight(list);
                if (subtreeHeight > maxSubtreeHeight) {
                    maxSubtreeHeight = subtreeHeight;
                }
            }
            return maxSubtreeHeight + 1;
        }
    }

    boolean isRoot = true;
    ArrayList<Integer> inferiors = new ArrayList<Integer>();
}