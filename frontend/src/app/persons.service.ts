import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Person } from './person/person';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class PersonsService {

  constructor(private http: HttpClient) {

  }

  createPerson(person : Person) : Observable<Person> {
    return this.http.post<Person>('http://localhost:8080/person', person);
  }

  getAllPersons() : Observable<any> {
    return this.http.get<any>('http://localhost:8080/person?name=');
  }

  getPersonById(id: number) : Observable<Person> {
    return this.http.get<any>(`http://localhost:8080/person/${id}`)
  }

  updatePerson(person : Person) : Observable<any> {
    return this.http.put<Person>(`http://localhost:8080/person/${person.id}`, person);
  }

  deletePerson(id : number) : Observable<any> {
    return this.http.delete<any>(`http://localhost:8080/person/${id}`);
  }

  findPersonNameContains(name: String) : Observable<any> {
    return this.http.get<any>(`http://localhost:8080/person?name=${name}`);
  }


}
