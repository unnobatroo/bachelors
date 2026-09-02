#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "pet.h"

int main(void)
{
    struct PetList *pet_list = pet_list_create();
    if (pet_list == NULL)
    {
        return 0;
    }
    pet_list_add(pet_list, pet_create("Tweety", PET_CATEGORY_BIRD));
    pet_list_add(pet_list, pet_create("Sylvester", PET_CATEGORY_CAT));
    pet_list_add(pet_list, pet_create("Hector", PET_CATEGORY_DOG));

    printf("Count: %d\n", pet_list_count(pet_list));

    for (int i = 0; i < pet_list_count(pet_list); i++)
    {
        pet_print(pet_list_get(pet_list, i));
    }

    pet_list_free(pet_list);
    return 0;
}