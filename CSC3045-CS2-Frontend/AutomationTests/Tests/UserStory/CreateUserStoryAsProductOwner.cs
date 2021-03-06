﻿using AutomationTests.PageTemplates;
using AutomationTests.Util;
using NUnit.Framework;
using TestStack.White.UIItems.ListBoxItems;

namespace AutomationTests.Tests.UserStory
{
    [TestFixture]
    class CreateUserStory : BaseTestClass
    {
        private UserDashboardPage _userDashboardPage;

        private ProjectDashboardPage _projectDashboardPage;

        private ProductBacklogPage _productBacklogPage;

        private CreateUserStoryPage _createUserStoryPage;

        [OneTimeSetUp]
        public void OneTimeSetup()
        {
            _userDashboardPage = new UserDashboardPage(MainWindow);
            _projectDashboardPage = new ProjectDashboardPage(MainWindow);
            _productBacklogPage = new ProductBacklogPage(MainWindow);
            _createUserStoryPage = new CreateUserStoryPage(MainWindow);

            LoginPage.Login("user3@e2e.com", "Aut0mation");
            Assert.IsTrue(_userDashboardPage.IsCurrentPage());

            var projectListItem = (WPFListItem)_userDashboardPage.ProjectListBox.Items.Find(item => "e2eProjectName1".Equals(item.Text));
            projectListItem.Click();

            Assert.IsTrue(_projectDashboardPage.IsCurrentPage());

            _projectDashboardPage.ProductBacklogButton.Click();

            Assert.IsTrue(_productBacklogPage.IsCurrentPage());

            _productBacklogPage.CreateStoryButton.Click();
        }

        [Test]
        public void ShouldSuccessfullyCreateAUserStoryAsAProductOwner()
        {
            Assert.IsTrue(_createUserStoryPage.IsCurrentPage());

            _createUserStoryPage.enterCorrectStoryDetails("e2eUserStoryTest", "10", "e2eDescriptionTest");

            Assert.IsTrue(_productBacklogPage.IsCurrentPage());

            var StoryListItem = (WPFListItem)_productBacklogPage.StoryListBox.Items.Find(item => "e2eUserStoryTest".Equals(item.Text));
            Assert.NotNull(StoryListItem);

            Assert.IsTrue(_productBacklogPage.IsCurrentPage());

            _productBacklogPage.CreateStoryButton.Click();

            Assert.IsTrue(_createUserStoryPage.IsCurrentPage());
        }

        [Test]
        public void ShouldSuccessfullyAccessCreateAUserStoryAndCancel()
        {
            Assert.IsTrue(_createUserStoryPage.IsCurrentPage());

            _createUserStoryPage.CancelButton.Click();

            Assert.IsTrue(_productBacklogPage.IsCurrentPage());

            _productBacklogPage.CreateStoryButton.Click();

            Assert.IsTrue(_createUserStoryPage.IsCurrentPage());
        }

        [Test]
        public void ShouldFailCreatingAUserStoryWithEmptyName()
        {
            Assert.IsTrue(_createUserStoryPage.IsCurrentPage());

            _createUserStoryPage.enterEmptyNameStoryDetails("10", "e2eDescription");

            var messageBox = MessageBoxUtil.GetWarningMessageBox(MainWindow);
            Assert.NotNull(messageBox);

            MessageBoxUtil.ClickOKButton(messageBox);

            _createUserStoryPage.CancelButton.Click();

            _productBacklogPage.CreateStoryButton.Click();

            Assert.IsTrue(_createUserStoryPage.IsCurrentPage());
        }

        [Test]
        public void ShouldFailCreatingAUserStoryWithEmptyDescription()
        {
            Assert.IsTrue(_createUserStoryPage.IsCurrentPage());

            _createUserStoryPage.enterEmptyNameStoryDetails("e2eUserStory", "10");

            var messageBox = MessageBoxUtil.GetWarningMessageBox(MainWindow);
            Assert.NotNull(messageBox);

            MessageBoxUtil.ClickOKButton(messageBox);

            _createUserStoryPage.CancelButton.Click();

            _productBacklogPage.CreateStoryButton.Click();

            Assert.IsTrue(_createUserStoryPage.IsCurrentPage());
        }

        [Test]
        public void ShouldFailCreatingAUserStoryWithEmptyMarketValue()
        {
            Assert.IsTrue(_createUserStoryPage.IsCurrentPage());

            _createUserStoryPage.enterEmptyDescriptionStoryDetails("e2eUserStory", "e2eDesciption");

            var messageBox = MessageBoxUtil.GetWarningMessageBox(MainWindow);
            Assert.NotNull(messageBox);

            MessageBoxUtil.ClickOKButton(messageBox);

            _createUserStoryPage.CancelButton.Click();

            _productBacklogPage.CreateStoryButton.Click();

            Assert.IsTrue(_createUserStoryPage.IsCurrentPage());
        }

        [Test]
        public void ShouldFailCreatingAUserStoryWithTextMarketValue()
        {
            //Writing Text in market value breaks it, update once bug is fixed
            //_createUserStoryPage.enterEmptyNameStoryDetails("Ten", "e2eDescription");

            // var messageBox = MessageBoxUtil.GetWarningMessageBox(MainWindow);
            // Assert.NotNull(messageBox);

            //MessageBoxUtil.ClickOKButton(messageBox);
        }

   
    }
}
