using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TestStack.White.UIItems;
using TestStack.White.UIItems.WindowItems;

namespace AutomationTests.PageTemplates
{
    public class LoginPage : BasePage
    {
        public TextBox UsernameTextBox
        {
            get { return MainWindow.Get<TextBox>("UsernameTextBox"); }
        }
        public TextBox PasswordTextBox
        {
            get { return MainWindow.Get<TextBox>("PasswordTextBox"); }
        }
        public Button RegisterButton
        {
            get { return MainWindow.Get<Button>("RegisterButton"); }
        }
        public Button LoginButton
        {
            get { return MainWindow.Get<Button>("LoginButton"); }
        }

        public LoginPage(Window window) : base(window)
        {

        }

        public override bool IsCurrentPage()
        {
            return PageTitle.Text.Equals("Registration");
        }
        
        public void Login(string username, string password)
        {
            UsernameTextBox.Text = username;
            PasswordTextBox.Text = password;

            LoginButton.Click();
        }
    }
}
