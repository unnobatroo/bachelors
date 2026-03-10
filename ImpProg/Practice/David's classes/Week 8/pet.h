#ifndef PET_H
#define PET_H

enum PetCategory
{
    PET_CATEGORY_DOG,
    PET_CATEGORY_CAT,
    PET_CATEGORY_BIRD,
    PET_CATEGORY_FISH,
};

struct Pet;
struct PetListItem;
struct PetList;

extern const char *const PetCategoryNames[];
struct Pet pet_create(const char *name, enum PetCategory pet_category);
void pet_print(struct Pet p);
struct PetList *pet_list_create();
void pet_list_free(struct PetList *petList);
struct Pet pet_list_get(struct PetList *pet_list, int index);
void pet_list_add(struct PetList *pet_list, struct Pet *pet);

#endif // PET_H