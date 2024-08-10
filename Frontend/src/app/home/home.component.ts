import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { ForgetPasswordComponent } from '../forget-password/forget-password.component';
import { LoginComponent } from '../login/login.component';
import { UserService } from '../Services/user.service';
import { SignupComponent } from '../signup/signup.component';
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(private dialog:MatDialog,
    private userServices:UserService,
    private router:Router) { }

  ngOnInit(): void {
    this.userServices.checkToken().subscribe((response:any)=>{
      this.router.navigate(['/cafe/dashboard']);
    },(error:any)=>{
      console.log(error);
    })
  }
  
  handleSignupAction(){
    const dialogConfig = new MatDialogConfig();
    dialogConfig.width = "550px";
    this.dialog.open(SignupComponent,dialogConfig);
  }

  handleForgetPasswordAction(){
    const dialogConfig = new MatDialogConfig();
    dialogConfig.width = "550px";
    this.dialog.open(ForgetPasswordComponent,dialogConfig);
  }

  handleloginAction(){
    const dialogConfig = new MatDialogConfig();
    dialogConfig.width = "550px";
    this.dialog.open(LoginComponent,dialogConfig);
  }


}
