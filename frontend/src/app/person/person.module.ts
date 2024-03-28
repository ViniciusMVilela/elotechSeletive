import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PersonRoutingModule } from './person-routing.module';
import { PersonFormComponent } from './person-form/person-form.component';
import { FormsModule } from '@angular/forms';
import { PersonListComponent } from './person-list/person-list.component';


@NgModule({
  declarations: [
    PersonFormComponent,
    PersonListComponent
  ],
  imports: [
    CommonModule,
    PersonRoutingModule,
    FormsModule
  ], exports: [
    PersonFormComponent,
    PersonListComponent
  ]
})
export class PersonModule { }
