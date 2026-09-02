#include <stdio.h>

float arr_avg(const int* arr, int len);
float arr_avg_ptr(int* arr, int len);
void print_multiplication_table(int* arr, int len);

int main(){
    int arr[] = {0};
    int len = sizeof(arr) / sizeof(arr[0]);

    printf("The average of an entire array is: %.2f\n", arr_avg(arr, len));
    printf("The average of a half of an array is: %.2f\n", arr_avg(arr, len/2));
    
    int arr2[100] = {0};
    int table_size = 10;

    print_multiplication_table(arr2, table_size);

    return 0;
}

float arr_avg(const int* arr, int len){
    int sum = 0;

    if (len <= 0){
        return 0.0f;
    }

    for (int i = 0; i < len; i++){
        sum += arr[i];
    }

    return (float)sum / len;
}

float arr_avg_ptr(int* arr, int len){
    if (len <= 0){
        return 0.0f;
    }

    int* current_ptr = arr;
    int* end_ptr = arr + len;
    int sum = 0;

    while (current_ptr < end_ptr){
        sum += *current_ptr;
        current_ptr++;
    }

    return (float)sum / len;
}

void print_multiplication_table(int* arr, int len){
    int* curr_elem = arr;
    for (int i = 1; i <= len; i++){
        for (int j = 1; j <= len; j++){
            *curr_elem = i*j;
            printf("%3d ", *curr_elem); // why can't I do curr_elem[j]?
            curr_elem++;
        }

        printf("\n");
    }
}