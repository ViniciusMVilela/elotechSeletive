import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, Params  } from '@angular/router';
import { Person } from '../person';
import { PersonsService } from '../../persons.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-person-form',
  templateUrl: './person-form.component.html',
  styleUrl: './person-form.component.css'
})
export class PersonFormComponent {

  person: Person;
  sucess : boolean = false;
  errors : any;
  // error: boolean = false;
  id: number;


  constructor(
    private service: PersonsService ,
    private router: Router,
    private activatedRoute: ActivatedRoute) {
    this.resetForm();

  }

  ngOnInit() : void {
    let params : Observable<Params>  = this.activatedRoute.params;
    params.subscribe(urlParams => {
      this.id = urlParams['id'];
      if(this.id) {
      this.service.getPersonById(this.id)
      .subscribe(response => this.person = response,
        errorResponse => this.person = new Person()
        )
      }
    })
  }

  resetForm(): void {
    this.person = {
      id: 0,
      name: '',
      cpf: '',
      birthDate: '',
      contactList: [{
        id: 0,
        nameContact: '',
        telephone: '',
        email: ''
      }]
    };
  }

  addContact(): void {
    this.person.contactList.push({
      id: 0,
      nameContact: '',
      telephone: '',
      email: ''
    });
  }
  removeContact(index: number): void {
    this.person.contactList.splice(index, 1);
  }

 returnPersonList() {
  this.router.navigate(['/person-list']);
 }


  onSubmit() {
    if(this.id) {
      this.service.updatePerson(this.person)
      .subscribe(response => {
        this.sucess = true;
        // this.erros = nul;
      }, errorResponse => {
        // this.errors = ["Erro ao atualizar o cliente";]
      })


    } else {
      this.service.createPerson(this.person)
      .subscribe( response => {
          console.log(response);
          this.resetForm();
          this.sucess = true;
          this.errors = false;
        },
        erroResponse => {
          // this.error = true;
          this.sucess = false;
          // this.errors = erroResponse.errors.error;
          console.log(erroResponse.error);
          this.errors = erroResponse.error;
        }
      )
    }
  }

}
