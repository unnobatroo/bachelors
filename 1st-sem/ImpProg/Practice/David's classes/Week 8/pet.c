#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "pet.h"

struct Pet
{
    char name[33];
    enum PetCategory category;
};

struct PetListItem
{
    struct Pet pet;
    struct PetListItem *next;
};

struct PetList
{
    int count;
    struct PetListItem *first;
    struct PetListItem *last;
};

const char *const PetCategoryNames[] = {
    "dog", "cat", "bird", "fish"};

struct Pet pet_create(const char *name, enum PetCategory pet_category)
{
    struct Pet result = {0};
    strncpy(result.name, name, sizeof(result.name));
    result.category = pet_category;
    return result;
}

void pet_print(struct Pet p)
{
    printf("%s is a %s.\n", p.name, PetCategoryNames[p.category]);
}

void pet_list_free(struct PetList *petList)
{
    struct PetListItem *current = petList->first;
    while (current != NULL)
    {
        struct PetListItem *next = current->next;
        free(current);
        current = next;
    }
    free(petList);
}

struct PetList *pet_list_create()
{
    struct PetList *petList = malloc(sizeof(struct PetList));
    if (petList == NULL)
    {
        return NULL;
    }

    petList->count = 0;
    petList->first = NULL;
    petList->last = NULL;

    return petList;
}

struct Pet pet_list_get(struct PetList *pet_list, int index)
{
    struct PetListItem *current = pet_list->first;
    for (int i = 0; i < index; i++)
    {
        current = current->next;
    }
    return current->pet;
}

void pet_list_add(struct PetList *pet_list, struct Pet *pet)
{
    struct PetListItem *item = malloc(sizeof(struct PetListItem));

    if (item == NULL)
    {
        printf("unable to allocate memory\n");
        return;
    }

    item->pet = *pet;
    item->next = NULL;

    pet_list->count++;
    if (pet_list->count == 1)
    {
        pet_list->first = item;
        pet_list->last = item;
    }
    else
    {
        pet_list->last->next = item;
        pet_list->last = item;
    }
}