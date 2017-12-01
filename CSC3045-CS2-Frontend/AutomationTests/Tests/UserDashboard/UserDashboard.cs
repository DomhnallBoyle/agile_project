using AutomationTests.PageTemplates;
using AutomationTests.Util;
using NUnit.Framework;
using System.Drawing;
using TestStack.White.UIItems;
using TestStack.White.UIItems.ListBoxItems;
using TestStack.White.UIItems.WPFUIItems;

namespace AutomationTests.Tests.UserDashboard
{
    class UserDashboard : BaseTestClass
    {
        private UserDashboardPage _userDashboardPage;
        private LoginPage _loginPage;

        [OneTimeSetUp]
        public void OneTimeSetup()
        {
            _userDashboardPage = new UserDashboardPage(MainWindow);
            _loginPage = new LoginPage(MainWindow);

            LoginPage.Login("user2@e2e.com", "Aut0mation");
            Assert.IsTrue(_userDashboardPage.IsCurrentPage());
        }

        [Test]
        public void ShouldSuccessfullyDisplayProjectName()
        {
           Assert.IsTrue(_userDashboardPage.IsCurrentPage());
            var projectListItem = (WPFListItem)_userDashboardPage.ProjectListBox.Items.Find(item => "e2eProjectName1".Equals(item.Text));
        }

        [Test]
        public void ShouldSuccessfullyDisplayProjectDescription()
        {         
            Assert.IsTrue(_userDashboardPage.IsCurrentPage());
            var projectListItem = (WPFListItem)_userDashboardPage.ProjectListBox.Items.Find(item => "e2eProjectDescription1".Equals(item.Text));
        }

     

        [Test]
        public void ShouldSuccessfullyDisplayManagerAsRole()
        {      
            Assert.IsTrue(_userDashboardPage.IsCurrentPage());
            var projectListItem = (WPFListItem)_userDashboardPage.ProjectListBox.Items.Find(item => "e2eProjectName1".Equals(item.Text));
            foreach (WPFListItem item in _userDashboardPage.ProjectListBox.Items)
            {
                Assert.True(item.Get<TestStack.White.UIItems.Image>("TeamMemberListManagerIcon").Visible);
                Assert.False(item.Get<TestStack.White.UIItems.Image>("TeamMemberListScrumMasterIcon").Visible);
                Assert.False(item.Get<TestStack.White.UIItems.Image>("TeamMemberListDeveloperIcon").Visible);
                Assert.False(item.Get<TestStack.White.UIItems.Image>("TeamMemberListProductOwnerIcon").Visible);
            }     
        }
    }
}
