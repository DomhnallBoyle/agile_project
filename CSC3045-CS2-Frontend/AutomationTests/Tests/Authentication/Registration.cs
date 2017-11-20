using AutomationTests.PageTemplates;
using AutomationTests.Util;
using NUnit.Framework;
using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using TestStack.White.UIItems;

namespace AutomationTests.Authentication
{
    [TestFixture]
    public class Registration : BaseTestClass
    {
        private string _guid;
        
        private RegisterPage RegisterPage;

        [OneTimeSetUp]
        public void OneTimeSetupRegistration()
        {
            _guid = Guid.NewGuid().ToString().Substring(0, 8);
            
            RegisterPage = new RegisterPage(MainWindow);

            LoginPage.RegisterButton.Click();
            Assert.IsTrue(RegisterPage.IsCurrentPage());
        }

        [Test]
        public void ShouldFailRegistrationWithEmptyEmail()
        {
            EnterValidCredentials();

            RegisterPage.EmailTextBox.Text = "";
            RegisterPage.RegisterButton.Click();

            var messageBox = MainWindow.MessageBox("Warning");
            Assert.NotNull(messageBox);
            Assert.IsTrue(MessageBoxUtil.GetTextContent(messageBox).Contains("empty"));

            MessageBoxUtil.ClickOKButton(messageBox);
        }

        [Test]
        public void ShouldFailRegistrationWithEmptyFirstName()
        {
            EnterValidCredentials();

            RegisterPage.FirstnameTextBox.Text = "";
            RegisterPage.RegisterButton.Click();

            var messageBox = MainWindow.MessageBox("Warning");
            Assert.NotNull(messageBox);
            Assert.IsTrue(MessageBoxUtil.GetTextContent(messageBox).Contains("empty"));

            MessageBoxUtil.ClickOKButton(messageBox);
        }

        [Test]
        public void ShouldFailRegistrationWithEmptyPasswords()
        {
            EnterValidCredentials();

            RegisterPage.PasswordTextBox.Text = "";
            RegisterPage.ConfirmPasswordTextBox.Text = "";
            RegisterPage.RegisterButton.Click();

            var messageBox = MainWindow.MessageBox("Warning");
            Assert.NotNull(messageBox);
            Assert.IsTrue(MessageBoxUtil.GetTextContent(messageBox).Contains("empty"));

            MessageBoxUtil.ClickOKButton(messageBox);
        }

        [Test]
        public void ShouldFailRegistrationWithMismatchedPasswords()
        {
            EnterValidCredentials();

            RegisterPage.ConfirmPasswordTextBox.Text = "wrongpassword";
            RegisterPage.RegisterButton.Click();

            var messageBox = MainWindow.MessageBox("Warning");
            Assert.NotNull(messageBox);
            Assert.IsTrue(MessageBoxUtil.GetTextContent(messageBox).Contains("don't match"));

            MessageBoxUtil.ClickOKButton(messageBox);
        }

        [Test]
        public void ShouldFailRegistrationWithInvalidServerSideValidation()
        {
            EnterValidCredentials();

            RegisterPage.EmailTextBox.Text = "Not an email";
            RegisterPage.RegisterButton.Click();

            var messageBox = MainWindow.MessageBox("Failure");
            Assert.NotNull(messageBox);

            MessageBoxUtil.ClickOKButton(messageBox);
        }

        [Test]
        public void ShouldSuccessfullyRegisterWithValidCredentials()
        {
            EnterValidCredentials();

            RegisterPage.DeveloperCheckBox.Select();
            RegisterPage.ScrumMasterCheckBox.Select();
            RegisterPage.ProductOwnerCheckBox.Select();

            Assert.IsTrue(RegisterPage.DeveloperCheckBox.IsSelected);
            Assert.IsTrue(RegisterPage.ScrumMasterCheckBox.IsSelected);
            Assert.IsTrue(RegisterPage.ProductOwnerCheckBox.IsSelected);

            RegisterPage.RegisterButton.Click();

            var messageBox = MainWindow.MessageBox("Success");
            Assert.NotNull(messageBox);

            MessageBoxUtil.ClickOKButton(messageBox);
            Assert.IsTrue(LoginPage.IsCurrentPage());

            LoginPage.RegisterButton.Click();
        }

        // ###############
        // ## Non-test helper methods
        // ###############

        private void EnterValidCredentials()
        {
            string password = "E2Euserpassw0rd";

            RegisterPage.EmailTextBox.Text = "e2e.user.email." + _guid + "@cs2.test";
            RegisterPage.FirstnameTextBox.Text = "E2EUserFirstName";
            RegisterPage.SurnameTextBox.Text = "E2EUserSurname";
            RegisterPage.PasswordTextBox.Text = password;
            RegisterPage.ConfirmPasswordTextBox.Text = password;
        }
    }
}
