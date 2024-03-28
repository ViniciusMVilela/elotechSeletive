import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Person } from '../person';
import { PersonsService } from '../../persons.service';
import { response } from 'express';

@Component({
  selector: 'app-person-list',
  templateUrl: './person-list.component.html',
  styleUrl: './person-list.component.css'
})
export class PersonListComponent implements OnInit {

  persons: Person[] = [];
  person: Person;
  personSelect : Person;
  sucessMesage: String;
  errorMesage: String;
  personName: String;
  nameFilter: String;
  personId : number;


  constructor(private service: PersonsService,
    private router: Router) {
  }

  ngOnInit() : void {
    this.service.getAllPersons().subscribe(response => this.persons = response.content);
  }

  newPerson() {
    this.router.navigate(['/person-form'])
  }

  prepareDelet(person : Person) {
    this.personSelect = person;
  }

  deletePerson() {
    this.service.deletePerson(this.personSelect.id)
    .subscribe(response => {
      this.sucessMesage = 'Person deleted'
      this.ngOnInit();
    },
    error => this.errorMesage = 'Error'
   )
  }

  findPersonNameContains() {
    if(this.nameFilter.trim() === "") {
      this.service.getAllPersons().subscribe(response => this.persons = response.content);
    } else {
      this.service.findPersonNameContains(this.nameFilter).subscribe(response => this.persons = response.content);
    }
  }

}
